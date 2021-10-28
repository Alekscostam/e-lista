package com.app.elista.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("app/")
public class ApiController {

    @GetMapping("calendar")
    public String calendar() {

        return "calendar";
    }

    @GetMapping("attendanceList")
    public String attendanceList() {

        return "attendanceList";
    }


}
