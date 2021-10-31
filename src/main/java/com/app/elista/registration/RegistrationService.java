package com.app.elista.registration;

import com.app.elista.appcompany.AppCompany;
import com.app.elista.appcompany.AppCompanyRole;
import com.app.elista.appcompany.AppCompanyService;
import com.app.elista.email.EmailSender;
import com.app.elista.email.EmailService;
import com.app.elista.registration.token.ConfirmationToken;
import com.app.elista.registration.token.ConfirmationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
public class RegistrationService {

    private final AppCompanyService appCompanyService;
    private final EmailValidator emailValidator;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailService emailService;

    @Autowired
    public RegistrationService(AppCompanyService appCompanyService, EmailValidator emailValidator, ConfirmationTokenService confirmationTokenService, EmailService emailService) {
        this.appCompanyService = appCompanyService;
        this.emailValidator = emailValidator;
        this.confirmationTokenService = confirmationTokenService;
        this.emailService = emailService;
    }

    public String register(RegistrationRequest request) throws MessagingException {
        boolean isValidEmail = emailValidator.
                test(request.getEmail());

        if (!isValidEmail) {
            throw new IllegalStateException("email not valid");
        }
        String token = "";
     try{
         token = appCompanyService.signUpCompany(
                 new AppCompany(
                         request.getName(),
                         request.getEmail(),
                         request.getPassword(),
                         request.getAddress(),
                         request.getPhone(),
                         request.getOffer(),
                         request.getCreationDate(),
                         AppCompanyRole.USER
                 )
         );
         String link = "https://e-lista.herokuapp.com/registration/confirm?token=" + token;
//        String link = "http://localhost:8095/registration/confirm?token=" + token;
         emailService.sendMail(
                 request.getEmail(),"Proszę potwierdzić email!",link,false);

         return "Proszę potwierdzić adres email";
     }
     catch (IllegalStateException illegalStateException)
     {
         return "Emial został już wzięty";
     }




//        return token;
    }

    @Transactional
    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token)
                .orElseThrow(() ->
                        new IllegalStateException("token not found"));

        if (confirmationToken.getConfirmedAt() != null) {
//            throw new IllegalStateException("email already confirmed");
        return "Email został juz potwierdzony";
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
//            throw new IllegalStateException("token expired");
        return "Sesja wygasła";
        }

        confirmationTokenService.setConfirmedAt(token);
        appCompanyService.enableAppCompany(
                confirmationToken.getAppCompany().getEmail());
        return "loginapp";
    }


}
