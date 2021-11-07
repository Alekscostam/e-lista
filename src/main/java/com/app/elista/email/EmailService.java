package com.app.elista.email;


import com.app.elista.appcompany.AppCompany;
import com.app.elista.registration.token.ConfirmationToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Locale;


@Service
public class EmailService {

    private JavaMailSender mailSender;

    @Autowired
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    // TODO: 23.10.2021


    public void sendMail(String to,
                         String subject,
                         String text,
                         boolean isHtmlContent) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(text, isHtmlContent);
        mailSender.send(mimeMessage);
    }



    public void constructResetTokenEmail(
            ConfirmationToken confirmationToken) throws MessagingException {
        AppCompany appCompany = confirmationToken.getAppCompany();
        String email = appCompany.getEmail();
        String token = confirmationToken.getToken();
        String url =  "http://localhost:8096/login/changePassword?token=" + token;
        String txt = "Wejdź w link poniżej aby zmienić hasło";
        String subject = "Zmiana hasła";
        sendMail(email, subject,txt + " \r\n" + url, false);
    }

}
