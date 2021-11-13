package com.app.elista.Services;

import com.app.elista.appcompany.AppCompany;
import com.app.elista.email.EmailService;
import com.app.elista.registration.token.ConfirmationToken;
import com.app.elista.registration.token.ConfirmationTokenService;
import com.app.elista.registration.token.TokenIsExpiredException;
import com.app.elista.registration.token.TokenNotFoundException;
import com.app.elista.repositories.AppCompanyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class AppCompanyService implements UserDetailsService {

    private final static String COMPANY_NOT_FOUND_MSG = "Działaność z tym emailem %s nie istnieje";
    private final AppCompanyRepository appCompanyRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailService emailService;
    private static Logger LOGGER = LoggerFactory.getLogger(AppCompanyService.class);;
    private static final String NOTVALID="NOTVALID";
    private static final String EXPIRED="EXPIRED";

    @Autowired
    public AppCompanyService(AppCompanyRepository appCompanyRepository, BCryptPasswordEncoder bCryptPasswordEncoder, ConfirmationTokenService confirmationTokenService, EmailService emailService) {
        this.appCompanyRepository = appCompanyRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.confirmationTokenService = confirmationTokenService;
        this.emailService = emailService;
    }


    public void updatePassword(String token,String password) throws TokenNotFoundException, TokenIsExpiredException {

        Optional<ConfirmationToken> foundToken = confirmationTokenService.findByTokenName(token);
        if (!foundToken.isPresent()){
            LOGGER.error("Nieprawidłowy kod dostępu");
            throw new TokenNotFoundException(NOTVALID);
        }else
        {
            if (foundToken.get().getExpiresAt().isBefore(LocalDateTime.now())) {
                LOGGER.error("Kod dostępu do zmiany hasła wygasł!");
                LOGGER.info("Kod zostaje usunięty...");
                confirmationTokenService.removeToken(foundToken.get());
                LOGGER.info("Kod został usunięty!");
                throw new TokenIsExpiredException(EXPIRED);
            }
            else{
                AppCompany appCompany = foundToken.get().getAppCompany();

                String encodedPassword = bCryptPasswordEncoder
                        .encode(password);
                appCompany.setPassword(encodedPassword);
                appCompanyRepository.save(appCompany);
                confirmationTokenService.removeToken(foundToken.get());
            }
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return appCompanyRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(String.format(COMPANY_NOT_FOUND_MSG, email)));
    }

    public String signUpCompany(AppCompany appCompany) {
        boolean userExists = appCompanyRepository
                .findByEmail(appCompany.getEmail())
                .isPresent();

        if (userExists) {
            // TODO check of attributes are the same and
            // TODO if email not confirmed send confirmation email.
            throw new IllegalStateException("email already taken");
        }

        String encodedPassword = bCryptPasswordEncoder
                .encode(appCompany.getPassword());

        appCompany.setPassword(encodedPassword);

        appCompanyRepository.save(appCompany);

        String token = UUID.randomUUID().toString();

        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                appCompany
        );

        confirmationTokenService.saveConfirmationToken(
                confirmationToken);

        return token;
    }

    public int enableAppCompany(String email) {
        return appCompanyRepository.enableAppCompany(email);
    }

    public AppCompany getCompanyByEmail(String email) {


        Optional<AppCompany> companyByEmail = appCompanyRepository.findByEmail(email);
        if(companyByEmail.isPresent()){
            return companyByEmail.get();
        }
        else
         throw new UsernameNotFoundException("Użytkownik nie został znaleziony") ;
    }

    public ConfirmationToken createPasswordResetTokenForCompany(AppCompany appCompany, String token) {


        ConfirmationToken resetToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(60),
                appCompany
        );
        confirmationTokenService.saveConfirmationToken(resetToken);
        return resetToken;
    }

    public void sendResetPasswordEmail(AppCompany appCompany) throws MessagingException {

        String token = UUID.randomUUID().toString();
        ConfirmationToken passwordResetTokenForCompany = createPasswordResetTokenForCompany(appCompany, token);

        emailService.constructResetTokenEmail(passwordResetTokenForCompany);

    }

    public void forgottenPassword(String email) throws UsernameNotFoundException , MessagingException {
        AppCompany companyByEmail = getCompanyByEmail(email);
        sendResetPasswordEmail(companyByEmail);
    }
}
