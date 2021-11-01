package com.app.elista.registration;

import com.app.elista.model.Offer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("registration")
public class RegistrationController {

    private String BUSY = "Email jest już zajęty!";
    private String CONFIRM = "Proszę sprawdzić email, aby potwierdzić rejestrację!";
    private String NOTVALID = "Email nieprawidłowy";

    private final RegistrationService registrationService;

    @Autowired
    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView registration(String message) {

        ModelAndView mav = new ModelAndView("registration");
        if (!(message == null)) {
            if (message.equals("BUSY"))
                mav.addObject("message", BUSY);
            else if(message.equals("CONFIRM"))
            mav.addObject("message", CONFIRM);
            else
            mav.addObject("message", NOTVALID);
        } else
            mav.addObject("message", "");
        return mav;
    }
//    @RequestMapping( method = RequestMethod.GET)
//    public ModelAndView registrationMSG(String message) {
//        ModelAndView mav = new ModelAndView("registration");
//        mav.addObject("message", message);
//        return mav;
//    }
//    @GetMapping
//    public String registration()
//    {
//        return "registration";
//    }

    @PostMapping
//    @ResponseBody
    public String register(@RequestParam String name,
                           @RequestParam String email,
                           @RequestParam String password,
                           @RequestParam String address,
                           @RequestParam String phone,
                           @RequestParam String offer
    ) throws MessagingException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        RegistrationRequest request = new RegistrationRequest(name, email, password, address, phone, Offer.valueOf(offer));
        LocalDateTime now = LocalDateTime.now();
        request.setCreationDate(dtf.format(now));
        String register = registrationService.register(request);
        return "redirect:/registration?message=" + register;
    }

    @GetMapping(path = "confirm")
//    @ResponseBody
    public String confirm(@RequestParam("token") String token) {
        String confirmTokenLogin = registrationService.confirmToken(token);
        return confirmTokenLogin;
    }
}
