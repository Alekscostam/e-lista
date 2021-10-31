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

    @GetMapping("usersList")
    public String usersList() {
        return "usersList";
    }
    @GetMapping("addUser")
    public String addUser() {
        return "addUser";
    }
    @GetMapping("optionUserList")
    public String optionUserList() {
        return "optionUserList";
    }


}
