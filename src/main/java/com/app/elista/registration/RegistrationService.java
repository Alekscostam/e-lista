package com.app.elista.registration;

import com.app.elista.appcompany.AppCompany;
import com.app.elista.appcompany.AppCompanyRole;
import com.app.elista.Services.AppCompanyService;
import com.app.elista.email.EmailService;
import com.app.elista.registration.token.ConfirmationToken;
import com.app.elista.registration.token.ConfirmationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
public class RegistrationService {

    private static final String NOTVALID="NOTVALID";
    private static final String CONFIRM="CONFIRM";
    private static final String BUSY="BUSY";


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
//            throw new IllegalStateException("notValid");
            return NOTVALID;
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
//         String link = "http://localhost:8096/registration/confirm?token=" + token;

         String link = "https://e-lista.herokuapp.com/confirm?token=" + token;
         emailService.sendMail(
                 request.getEmail(),"Prosz?? potwierdzi?? email!",link,false);

         return CONFIRM;
     }
     catch (Exception exception)
     {
//         +exception.getMessage()
         return BUSY;
     }


    }

    @Transactional
    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token)
                .orElseThrow(() ->
                        new IllegalStateException("token not found"));


        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("----------->Email zosta?? ju?? potwierdzony<-----------");
//        return "Email zosta?? juz potwierdzony";
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("----------->Sesja wygas??a<-----------");

        }

        confirmationTokenService.setConfirmedAt(token);
        appCompanyService.enableAppCompany(
                confirmationToken.getAppCompany().getEmail());
        return "login";
    }


}
