package com.app.elista.registration;

import com.app.elista.model.Offer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("registration")
public class RegistrationController {

    private final RegistrationService registrationService;

    @Autowired
    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }
    @GetMapping
    public String registration()
    {
        return "registration";
    }
    @PostMapping
//    @ResponseBody
    public String register( @RequestParam String name,
                            @RequestParam String email,
                            @RequestParam String password,
                            @RequestParam String address,
                            @RequestParam String phone,
                            @RequestParam String offer
                            ) throws MessagingException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        RegistrationRequest request = new RegistrationRequest(name,email,password,address,phone,Offer.valueOf(offer));
        LocalDateTime now = LocalDateTime.now();
        request.setCreationDate(dtf.format(now));
        String registerMSG = registrationService.register(request);
        return "registration";
    }

    @GetMapping(path = "confirm")
    @ResponseBody
    public String confirm(@RequestParam("token") String token) {

            return registrationService.confirmToken(token);
    }
}
