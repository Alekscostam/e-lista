package com.app.elista.appcompany;

import com.app.elista.registration.token.ConfirmationToken;
import com.app.elista.registration.token.ConfirmationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AppCompanyService implements UserDetailsService {

    private final static String COMPANY_NOT_FOUND_MSG = "Działaność z tym emailem %s nie istnieje";
    private final AppCompanyRepository appCompanyRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;

    @Autowired
    public AppCompanyService(AppCompanyRepository appCompanyRepository, BCryptPasswordEncoder bCryptPasswordEncoder, ConfirmationTokenService confirmationTokenService) {
        this.appCompanyRepository = appCompanyRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.confirmationTokenService = confirmationTokenService;
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
}
