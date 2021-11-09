package com.app.elista.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("home")

public class HomeController {
    @GetMapping()
    public String home(){
        return "home";
    }

    @GetMapping("calendar")
    public ModelAndView calendar(String name){
        ModelAndView mav = new ModelAndView("Table/Kalendarz");
        mav.addObject("name",name);
        return mav;
    }
}
