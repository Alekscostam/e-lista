package com.app.elista.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("")
public class RedirectedController {

    @GetMapping()
    public String redirectToApp(){
        return "redirect:/app/calendar";
    }
}
