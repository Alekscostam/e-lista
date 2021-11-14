package com.app.elista.Controller;

import com.app.elista.Services.PriceService;
import com.app.elista.appcompany.AppCompany;
import com.app.elista.model.Prices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("app/")
public class ApiController {

    @Autowired
    private final PriceService priceService;

    public ApiController(PriceService priceService) {
        this.priceService = priceService;
    }

    @GetMapping("calendar")
    public String calendar(@AuthenticationPrincipal AppCompany appCompany) {
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

    @GetMapping("optionGroupList")
    public ModelAndView getGroups(@AuthenticationPrincipal AppCompany appCompany) {

        ModelAndView mav= new ModelAndView("optionGroupList");
        List<Prices> all = priceService.findAll();
        mav.addObject("prices",all);

        return mav;
    }

    @PostMapping("optionGroupList")
    public String postGroup(String imie) {


        return "optionGroupList";
    }

    @PostMapping("postPrice")
    public void postPrice( String name,
                            Integer value,
                           Short cycle,
                           String description ){

        Prices price = new Prices(name,value,cycle,description);

        priceService.addPrices(price);



//      @RequestBody Integer value,
//        @RequestBody Short cycle,
//        @RequestBody String dataFrom,
//        @RequestBody String dataTo,
//        @RequestBody String information

    }

}
