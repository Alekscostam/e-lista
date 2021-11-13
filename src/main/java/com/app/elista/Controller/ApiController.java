package com.app.elista.Controller;

import com.app.elista.Services.PriceService;
import com.app.elista.appcompany.AppCompany;
import com.app.elista.model.Prices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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
    public String getGroups(@AuthenticationPrincipal AppCompany appCompany) {

//        ModelAndView modelAndView= new ModelAndView(appCompany);
//        modelAndView.addObject()

        return "optionGroupList";
    }

    @PostMapping("optionGroupList")
    public String postGroup(String imie) {


        return "optionGroupList";
    }

    @PostMapping("postPrice")
    public void postPrice( String name,
                            Integer value,
                           Short cycle,
                           String dataFrom,
                           String dataTo,
                           String description ){

        Prices price = new Prices(name,value,cycle,dataFrom,dataTo,description);

        priceService.addPrices(price);



//      @RequestBody Integer value,
//        @RequestBody Short cycle,
//        @RequestBody String dataFrom,
//        @RequestBody String dataTo,
//        @RequestBody String information

    }

}
