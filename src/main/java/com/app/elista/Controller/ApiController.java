package com.app.elista.Controller;

import com.app.elista.Services.PriceService;
import com.app.elista.Services.TeamService;
import com.app.elista.Services.TermService;
import com.app.elista.appcompany.AppCompany;
import com.app.elista.model.Prices;
import com.app.elista.model.Teams;
import com.app.elista.model.Terms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("app/")
public class ApiController {


    @Autowired
    private final PriceService priceService;
    private final TermService termService;
    private final TeamService teamService;

    @Autowired
    public ApiController(PriceService priceService, TermService termService, TeamService teamService) {
        this.priceService = priceService;
        this.termService = termService;
        this.teamService = teamService;
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


    @GetMapping("optionUserList")
    public String optionUserList() {
        return "optionUserList";
    }

    @GetMapping("optionGroupList")
    public ModelAndView getGroups(@AuthenticationPrincipal AppCompany appCompany) {

        ModelAndView mav = new ModelAndView("optionGroupList");
        List<Prices> all = priceService.findAll();
        mav.addObject("prices", all);

        return mav;
    }

    @PostMapping("optionGroupList")
    public String postGroup(String imie) {


        return "optionGroupList";
    }

    @PostMapping("postPrice")
    public ModelAndView postPrice(String priceName,
                          Integer priceValue,
                          Short priceCycle,
                          String priceDescription) {
        Prices price = new Prices(priceName, priceValue, priceCycle, priceDescription);
        priceService.addPrices(price);

//        return "optionGroupList";
//      @RequestBody Integer value,
//        @RequestBody Short cycle,
//        @RequestBody String dataFrom,
//        @RequestBody String dataTo,
//        @RequestBody String information

        return new ModelAndView("redirect:/app/optionGroupList");

    }
    // TODO: 18.11.2021  DODAC DO TABELI HASHUJACEJ

    @PostMapping("postGroup")
    public ModelAndView postGroup(
            @AuthenticationPrincipal AppCompany appCompany,
            String groupName,
            String groupPlace,
            String groupSize,
            String groupLeader,
            String groupDataFrom,
            String groupDataTo,
            String groupDescription,
            String priceIds,
            String groupDayFor,
            String groupTimeFor,
            String groupColor,
            String groupFirstFree
            ) {


            if(groupColor==null)
            {
                groupColor ="ffffff";
            }

            List<String> listDays = divideStringToList(groupDayFor);
            List<String> listsTimes = divideStringToList(groupTimeFor);
            List<String> listsPriceIds = divideStringToList(priceIds);

            if(!listsPriceIds.isEmpty())
            {

                List<Prices> allPricesByPriceIds = priceService.findAllByPriceIds(listsPriceIds);
            }

            if (!listDays.isEmpty())
            {
                List<Terms> addedTermsList = termService.addTerms(stringListsToTermLists(listDays, listsTimes));
            }


            boolean resultFirstFree = Boolean.TRUE;

        if(groupFirstFree==null){
            resultFirstFree = Boolean.FALSE;
        }


        Teams teams = new Teams(
                groupName,
                groupLeader,
                groupPlace,
                groupDataFrom,
                groupDataTo,
                (short)0,
                Short.valueOf(groupSize),
                groupColor,
                resultFirstFree,
                groupDescription,
                appCompany);


        teamService.addGroup(teams);
        return new ModelAndView("redirect:/app/optionGroupList");

    }

    public List<String> divideStringToList(String elementToDivide) {

        if(elementToDivide!=null) {
            String[] split = elementToDivide.replaceAll("\\s+", "").split(",");
            return Arrays.asList(split);
        }
        else
            return Collections.emptyList();

    }



    public List<Terms> stringListsToTermLists(List<String> dayNameList,List<String> times) {

        List<Terms> termsList = new ArrayList<>();
        for (int i = 0; i < dayNameList.size(); i++)
        {
            Terms terms = new Terms(dayNameList.get(i),times.get(i));
            termsList.add(terms);
        }
        return termsList;
    }
}
