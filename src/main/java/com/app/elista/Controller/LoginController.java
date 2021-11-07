package com.app.elista.Controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("login")
public class LoginController {

    @GetMapping()
    public ModelAndView login(String error){

        ModelAndView mav = new ModelAndView("login");
        if (!(error == null)) {
            error="Nieprawidłowe dane!";
            mav.addObject("error", error);
            return mav;
        } else
            mav.addObject("error", "");
        return mav;
    }

//    @GetMapping("/change")
//    public String changePassword(){
//
//        return "login";
//    }





}
