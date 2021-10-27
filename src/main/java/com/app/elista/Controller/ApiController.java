package com.app.elista.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("app.")
public class ApiController {

    @GetMapping()
    public String calendar() {

        return "calendar";
    }
}
