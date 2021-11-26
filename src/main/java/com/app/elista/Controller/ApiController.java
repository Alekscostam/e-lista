package com.app.elista.Controller;

import com.app.elista.Services.*;
import com.app.elista.appcompany.AppCompany;
import com.app.elista.model.Prices;
import com.app.elista.model.Teams;
import com.app.elista.model.extended.AllInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@Controller
@RequestMapping("app/")
public class ApiController {
    private static Logger LOGGER = LoggerFactory.getLogger(GroupPriceService.class);;

    @Autowired
    private final PriceService priceService;
    private final TeamService teamService;
    private final GroupPriceService groupPriceService;

    public ApiController(PriceService priceService, TeamService teamService, GroupPriceService groupPriceService) {
        this.priceService = priceService;
        this.teamService = teamService;
        this.groupPriceService = groupPriceService;
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
        List<AllInfo> allByUUID = teamService.findAllByUUID(appCompany.getIdCompany());
        ModelAndView mav = new ModelAndView("optionGroupList");
        List<Prices> allPrices = priceService.findAllByAppCompanyId(appCompany.getIdCompany());
        mav.addObject("prices", allPrices);
        mav.addObject("allInfos", allByUUID);
        return mav;
    }

    @PostMapping("optionGroupList")
    public String postGroup(String imie) {


        return "optionGroupList";
    }

    @PostMapping("postPrice")
    public ModelAndView postPrice(
            String priceName,
                          Integer priceValue,
                          Short priceCycle,
                          String priceDescription,
                                  @AuthenticationPrincipal AppCompany appCompany) {

        Prices price = new Prices(appCompany,priceName, priceValue, priceCycle, priceDescription);
        priceService.addPrices(price);

        return new ModelAndView("redirect:/app/optionGroupList");

    }
    @PostMapping( "deletePrice")
    public ModelAndView deletePrice(String priceId) {
        try{
            Long id = Long.valueOf(priceId);
            groupPriceService.deletePriceFromGP(id);
            priceService.deletePriceById(id);

        }catch (NumberFormatException ex)
        {
            LOGGER.error(ex.getMessage(), "Nie zaznacozno żadnej opcji!" );
        }
        return new ModelAndView("redirect:/app/optionGroupList");
    }

    @PostMapping( "deleteGroup")
    public ModelAndView deleteGroup(String groupId) {
        try{
            Long id = Long.valueOf(groupId);
            groupPriceService.deleteGroupFromGP(id);
            teamService.deleteGroupById(id);

        }catch (NumberFormatException ex)
        {
            LOGGER.error(ex.getMessage()+ " - Nie zaznaczono zadnej opcji!" );
        }
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
            String groupTimeFrom,
            String groupTimeTo,
            String groupColor,
            String groupFirstFree
            ) {


            if(groupColor==null)
            {
                groupColor ="ffffff";
            }

            List<String> listsPriceIds = divideStringToList(priceIds);

            boolean resultFirstFree = Boolean.TRUE;

        if(groupFirstFree==null){
            resultFirstFree = Boolean.FALSE;
        }
        String addedTerms ="";
        if (!groupDayFor.isEmpty())
        {
           addedTerms = stringListsToString(groupDayFor, groupTimeFrom, groupTimeTo);
        }

            if (groupSize.isEmpty())
            {
                groupSize = "0";
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
                addedTerms,
                appCompany);

        Teams groupAdded = teamService.addGroup(teams);

        if(!listsPriceIds.isEmpty())
        {
            List<Prices> allPricesByPriceIds = priceService.findAllByPriceIds(listsPriceIds);
            groupPriceService.insertToGP(groupAdded,allPricesByPriceIds);
        }
        return new ModelAndView("redirect:/app/optionGroupList");
    }

    public List<String> divideStringToList(String elementToDivide) {

        if(elementToDivide!=null) {
            String[] splitOne = elementToDivide.replaceAll("\\s+", "").split(",");

            return Arrays.asList(splitOne);
        }
        else
            return Collections.emptyList();

    }


    public String stringListsToString(String dayToDivide, String elementToDivideOne, String elementToDivideTwo) {

       StringBuilder stringBuilder = new StringBuilder();

        List<String> dayNames = divideStringToList(dayToDivide);
        List<String> timesFrom = divideStringToList(elementToDivideOne);
        List<String> timesTo = divideStringToList(elementToDivideTwo);

        for (int i = 0; i < dayNames.size(); i++)
        {
            stringBuilder.append(dayNames.get(i)).append(" : ").append(timesFrom.get(i)).append(" - ").append(timesTo.get(i)).append(";");
        }
        return stringBuilder.toString();
    }
}
