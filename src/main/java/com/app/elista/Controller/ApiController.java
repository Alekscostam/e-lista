package com.app.elista.Controller;

import com.app.elista.Services.*;
import com.app.elista.appcompany.AppCompany;
import com.app.elista.model.Prices;
import com.app.elista.model.Teams;
import com.app.elista.model.Users;
import com.app.elista.model.extended.AllInfo;
import com.app.elista.model.extended.TermsPricesTeams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
@RequestMapping("app/")
public class ApiController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApiController.class);
    private static final String redirectedOptionGroupList = "redirect:/app/optionGroupList";
    private static final String redirectedAttendanceList= "redirect:/app/attendanceList";

    @Autowired
    private final PricesService pricesService;
    private final UsersService usersService;
    private final TeamsService teamsService;
    private final TeamsPricesService teamsPricesService;

    public ApiController(PricesService pricesService, UsersService usersService, TeamsService teamsService, TeamsPricesService teamsPricesService) {
        this.pricesService = pricesService;
        this.usersService = usersService;
        this.teamsService = teamsService;
        this.teamsPricesService = teamsPricesService;
    }

    @GetMapping("calendar")
    public String calendar(@AuthenticationPrincipal AppCompany appCompany) {
        return "calendar";
    }

    @GetMapping("attendanceList")
    public ModelAndView attendanceList(@AuthenticationPrincipal AppCompany appCompany) {
        List<Teams> teamIdsAndTeamNamesByUUID = teamsService.findTeamIdsAndTeamNamesByUUID(appCompany.getIdCompany());
        ModelAndView mav = new ModelAndView("attendanceList");
        mav.addObject("teams", teamIdsAndTeamNamesByUUID);

        return mav;
    }

    @GetMapping("usersList")
    public String usersList() {


        return "usersList";
    }


    @GetMapping("optionUserList")
    public ModelAndView optionUserList(@AuthenticationPrincipal AppCompany appCompany) {
        List<Teams> teamIdsAndTeamNamesByUUID = teamsService.findTeamIdsAndTeamNamesByUUID(appCompany.getIdCompany());
        ModelAndView mav = new ModelAndView("optionUserList");
        mav.addObject("teams", teamIdsAndTeamNamesByUUID);
        return mav;
    }

    @GetMapping("optionGroupList")
    public ModelAndView getGroups(@AuthenticationPrincipal AppCompany appCompany) {
        List<AllInfo> allByUUID = teamsService.findAllInformationsByTeamUUID(appCompany.getIdCompany());
        ModelAndView mav = new ModelAndView("optionGroupList");
        List<Prices> allPrices = pricesService.findAllPricesByAppCompanyId(appCompany.getIdCompany());
        mav.addObject("prices", allPrices);
        mav.addObject("allInfos", allByUUID);
        return mav;
    }

    @PostMapping("optionGroupList")
    public String postGroup(String imie) {
        return "optionGroupList";
    }

    @ResponseBody
    @PostMapping("postUser")
    public void postUser(
            String groupId,
            String priceId,
            String userName,
            String userSurname,
            String userPhone,
            String userEmail,
            String userOfAge,
            @AuthenticationPrincipal AppCompany appCompany
    ) {
        Prices price = pricesService.findByPriceId(priceId);
        Teams team = teamsService.findTeamById(groupId);

        Boolean adult;
        if (userOfAge == "true") {
            adult = true;
        } else {
            adult = false;
        }

        Users user = new Users(
                appCompany,
                userName,
                userSurname,
                Integer.valueOf(userPhone),
                userEmail,
                adult,
                "",
                "",
                getLocalDateTime(),
                team,
                price);

        usersService.saveUser(user);
        team.setFreeSpace((short) (team.getFreeSpace() + (short) 1));
        teamsService.saveTeam(team);

//        return redirectedAttendanceList;
    }


    @ResponseBody
    @GetMapping("/getUsers")
    public List<Users> getUsers(String groupId, @AuthenticationPrincipal AppCompany appCompany) {

        if (groupId.equals("all")) {
            return usersService.findAllUsersByAppCompanyId(String.valueOf(appCompany.getIdCompany()));
        }
        else if(groupId.equals("none")){
            // TODO: 06.12.2021 FIND USER WITHOUT GROUP
            return Collections.emptyList();
        }
        else {
            if (!groupId.isEmpty()) {
                return usersService.findAllUsersByGroupId(groupId);
            } else {
                return usersService.findAllUsersWithoutGroups(String.valueOf(appCompany.getIdCompany()));
            }
        }
    }

    @ResponseBody
    @PostMapping("/deleteUser")
    public void deleteUser(String userId, String groupId) {

        usersService.deleteUser(userId);
        Teams team = teamsService.findTeamById(groupId);
        team.setFreeSpace((short) (team.getFreeSpace() - (short) 1));
        teamsService.saveTeam(team);

    }

    @ResponseBody
    @GetMapping("/getSpecifiedGroupInformation")
    public TermsPricesTeams getTasksByProjectId(String groupId) {
        try {
            Teams team = teamsService.findTeamById(groupId);
            team.setAppCompany(null);
            List<String> termsForTeam = teamsService.getTermsForTeams(team);
            List<Prices> allPrices = pricesService.findAllPricesByTeam(team);
            return new TermsPricesTeams(termsForTeam, allPrices, team);
        } catch (NumberFormatException ne) {
            LOGGER.error(ne.getMessage());
            LOGGER.error("BRAK GROUPID");
            return null;
        }
    }

    @PostMapping("postPrice")
    public ModelAndView addPrice(
            String idPriceNumber,
            String priceName,
            Integer priceValue,
            Short priceCycle,
            String priceDescription,
            @AuthenticationPrincipal AppCompany appCompany) {

        if (idPriceNumber != null && !(idPriceNumber.isEmpty())) {
            Prices price = pricesService.findByPriceId(idPriceNumber);
            price.setName(priceName);
            price.setValue(priceValue);
            price.setDescription(priceDescription);
            price.setCycle(priceCycle);
            pricesService.savePrice(price);
        } else {
            Prices price = new Prices(appCompany, priceName, priceValue, priceCycle, priceDescription);
            pricesService.addPrices(price);
        }

        return new ModelAndView(redirectedOptionGroupList);
    }

    @PostMapping("deletePrice")
    public ModelAndView deletePrice(String priceId) {
        try {
            Long id = Long.valueOf(priceId);
            teamsPricesService.deletePriceFromGP(id);
            pricesService.deletePriceById(id);

        } catch (NumberFormatException ex) {
            LOGGER.error(ex.getMessage(), " - Nie zaznacozno żadnej opcji!");
        }
        return new ModelAndView(redirectedOptionGroupList);
    }

    @PostMapping("deleteGroup")
    public ModelAndView deleteGroup(String groupId) {
        try {
            Long id = Long.valueOf(groupId);
            teamsPricesService.deleteGroupFromGP(id);
            teamsService.deleteGroupById(id);
            LOGGER.info("Usunięto grupę");
        } catch (NumberFormatException ex) {
            LOGGER.error(ex.getMessage());
            LOGGER.error(" Nie zaznaczono zadnej opcji!");
        }
        return new ModelAndView(redirectedOptionGroupList);
    }

    @PostMapping("postGroup")
    public ModelAndView postGroup(
            @AuthenticationPrincipal AppCompany appCompany,
            String groupId,
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


        if (groupColor == null) {
            groupColor = "ffffff";
        }

        List<String> listsPriceIds = divideStringToList(priceIds);

        boolean resultFirstFree = Boolean.TRUE;

        if (groupFirstFree == null) {
            resultFirstFree = Boolean.FALSE;
        }
        String addedTerms = "";
        if (!groupDayFor.isEmpty()) {
            addedTerms = stringListsToString(groupDayFor, groupTimeFrom, groupTimeTo);
        }

        if (groupSize.isEmpty()) {
            groupSize = "0";
        }

        Teams team;

        if (groupId != null && !(groupId.isEmpty())) {
            team = teamsService.findTeamById(groupId);
            team.setTeamName(groupName);
            team.setLeaderName(groupLeader);
            team.setPlace(groupPlace);
            team.setStartDate(groupDataFrom);
            team.setEndDate(groupDataTo);
            team.setGroupSize(Short.valueOf(groupSize));
            team.setColor(groupColor);
            team.setDescription(groupDescription);
            team.setFirstFree(resultFirstFree);
            team.setTerms(addedTerms);

        } else {
            team = new Teams(
                    groupName,
                    groupLeader,
                    groupPlace,
                    groupDataFrom,
                    groupDataTo,
                    (short) 0,
                    Short.valueOf(groupSize),
                    groupColor,
                    resultFirstFree,
                    groupDescription,
                    addedTerms,
                    appCompany);
        }

        team = teamsService.saveTeam(team);
        if (!listsPriceIds.isEmpty()) {
            List<Prices> allPricesByPriceIds = pricesService.findAllByPriceIds(listsPriceIds);
            teamsPricesService.insertToGP(team, allPricesByPriceIds);
        }
        return new ModelAndView("redirect:/app/optionGroupList");
    }

    public List<String> divideStringToList(String elementToDivide) {

        if (elementToDivide != null) {
            String[] splitOne = elementToDivide.replaceAll("\\s+", "").split(",");
            return Arrays.asList(splitOne);

        } else
            return Collections.emptyList();

    }

    public String stringListsToString(String dayToDivide, String elementToDivideOne, String elementToDivideTwo) {

        StringBuilder stringBuilder = new StringBuilder();

        List<String> dayNames = divideStringToList(dayToDivide);
        List<String> timesFrom = divideStringToList(elementToDivideOne);
        List<String> timesTo = divideStringToList(elementToDivideTwo);

        for (int i = 0; i < dayNames.size(); i++) {
            stringBuilder.append(dayNames.get(i)).append(": ").append(timesFrom.get(i)).append(" - ").append(timesTo.get(i)).append(";");
        }
        return stringBuilder.toString();
    }

    public String getLocalDateTime() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);

    }
}
