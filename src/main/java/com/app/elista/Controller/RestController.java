package com.app.elista.Controller;

import org.springframework.web.bind.annotation.GetMapping;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    @GetMapping("/message")
    public String message(){
        return "message";
    }
}
