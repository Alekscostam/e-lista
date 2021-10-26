package com.app.elista.Controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("loginapp")
public class LoginController {
    @GetMapping()
    public String login(){
        return "login";
    }



}
