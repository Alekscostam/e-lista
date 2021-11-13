package com.app.elista.forgotPass;

import com.app.elista.Services.AppCompanyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PasswordResetController {

    private static Logger LOGGER = LoggerFactory.getLogger(PasswordResetController.class);;
    private static final String REDIRECT_LOGIN = "redirect:/login";
    private static final String MSGNotValidCode = "kod nie jest prawidłowy";
    private static final String MSGSessionRequired = "Kod dostępu do zmiany hasła wygasł! Prosimy o ponowne zgłoszenie zmiany hasła na maila ";

    @Autowired
    private MessageSource messageSource;
    @Autowired
    private AppCompanyService appCompanyService;

    @PostMapping("login/request")
    public String resetPassword (@RequestParam String email){

       try {
           appCompanyService.forgottenPassword(email);
       }catch (Exception e){
           LOGGER.error(e.getMessage());
       }
        return REDIRECT_LOGIN;
    }

    @GetMapping("login/changePassword")
    public ModelAndView changePasswordSite(@RequestParam("token") String token, String error) {
        ModelAndView mav = new ModelAndView("changePassword");
        mav.addObject("token", token);
        if(error!=null){
            if(error.equals("NOTVALID"))
                mav.addObject("error", MSGNotValidCode);
            else
                mav.addObject("error", MSGSessionRequired);

        }
        return mav;
    }

    @PostMapping("login/changePassword")
    public String changePassword(@RequestParam("token") String token,
                                 @RequestParam("password") String password)  {
        try {
            appCompanyService.updatePassword(token, password);
            LOGGER.info("Token jest prawidłowy");
            return "login";
        }
        catch (Exception ex)
        {
            LOGGER.error(ex.getMessage());
                return "redirect:changePassword?token="+token+"&error="+ex.getMessage();
        }
    }
}

