package com.app.elista.Controller;

import com.app.elista.Services.*;
import com.app.elista.appcompany.AppCompany;
import com.app.elista.model.*;
import com.app.elista.model.extended.AllInfo;
import com.app.elista.model.extended.TermsPricesTeams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
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
    private final DatesService datesService;
    private final DatesForGroupsService datesForGroupsService;

    public ApiController(PricesService pricesService, UsersService usersService, TeamsService teamsService, TeamsPricesService teamsPricesService, DatesService datesService , DatesForGroupsService datesForGroupsService) {
        this.pricesService = pricesService;
        this.usersService = usersService;
        this.teamsService = teamsService;
        this.teamsPricesService = teamsPricesService;
        this.datesService = datesService;
        this.datesForGroupsService = datesForGroupsService;
    }

    @GetMapping("calendar")
    public String calendar(@AuthenticationPrincipal AppCompany appCompany) {
        return "calendar";
    }

    @GetMapping("attendanceList")
    public ModelAndView attendanceList(@AuthenticationPrincipal AppCompany appCompany) {

        setDates(appCompany);

        List<Teams> teamIdsAndTeamNamesByUUID = teamsService.findTeamIdsAndTeamNamesByUUID(appCompany.getIdCompany());
        ModelAndView mav = new ModelAndView("attendanceList");
        mav.addObject("teams", teamIdsAndTeamNamesByUUID);

        return mav;
    }

    private void setDates(AppCompany appCompany) {
        String localDateTime = getLocalDateTime();
//        Dates dates = datesService.saveDate(localDateTime);
        Dates date = datesService.saveOrGetDateByLdt(localDateTime);

        String actuallyDayWeekText = getActuallyDayWeekText();
//        String actuallyDayWeekText = "Poniedziałek";

        List<Teams> allTeams = teamsService.findAllByCompanyWithoutAppCompanyReset(appCompany);
        List<TermsPricesTeams> termsPricesTeams  = new ArrayList<>();

//        Dates asd = new Dates()


        for (int i = 0; i < allTeams.size(); i++) {
            List<String> terms = teamsService.getTermsForTeams(allTeams.get(i));
            termsPricesTeams.add(new TermsPricesTeams(allTeams.get(i),terms));
        }
        for (int i = 0; i < termsPricesTeams.size(); i++) {
            for (int i1 = 0; i1 < termsPricesTeams.get(i).getTerms().size(); i1++) {
                if (termsPricesTeams.get(i).getTerms().get(i1).contains(actuallyDayWeekText)) {
                    Teams teams = termsPricesTeams.get(i).getTeams();
                    datesForGroupsService.saveDatesAndGroups(teams,date);
                }
                System.out.println("nie zawiera");
            }
        }
    }


    public String getActuallyDayWeekText(){
       Date date=new Date();
       Calendar c = Calendar.getInstance();
       c.setTime(date);
       int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
       String dayWeekText = new SimpleDateFormat("EEEE").format(date);
       System.out.println(dayWeekText);


       return dayWeekText.substring(0, 1).toUpperCase() + dayWeekText.substring(1);
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
    public ModelAndView optionGroupList(@AuthenticationPrincipal AppCompany appCompany) {
        List<AllInfo> allByUUID = teamsService.findAllInfoByAppCompanyUUID(appCompany.getIdCompany());
        ModelAndView mav = new ModelAndView("optionGroupList");
        List<Prices> allPrices = pricesService.findAllPricesByAppCompanyId(appCompany.getIdCompany());
        mav.addObject("prices", allPrices);
        mav.addObject("allInfos", allByUUID);
        return mav;
    }


    @GetMapping("getGroups")
    @ResponseBody
    public  List<AllInfo>  getGroups(@AuthenticationPrincipal AppCompany appCompany) {
        List<AllInfo> allByUUID = teamsService.findAllInfoByAppCompanyUUID(appCompany.getIdCompany());
        return allByUUID;
    }

    @ResponseBody
    @GetMapping("getPricesId")
    public  List<String> findInformationAboutGroupEditable(@AuthenticationPrincipal AppCompany appCompany, String groupId){
        List<String> idPricesByIdTeam = teamsPricesService.findIdPricesByIdTeam(groupId);

        return idPricesByIdTeam;
    }


    @ResponseBody
    @GetMapping("getPrices")
    public  List<Prices> getPrices(@AuthenticationPrincipal AppCompany appCompany){

        List<Prices> all = pricesService.findAllPricesByAppCompanyId(appCompany.getIdCompany());

        return all;
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
        Prices price = new Prices();
        if(!priceId.equals("undefined"))
        {
            price = pricesService.findByPriceId(priceId);
        }
        else{
            price.setIdPrice(null);
            price.setCycle((short)0);
            price.setDescription("");
            price.setValue(0);
            price.setName("");
        }

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
                price.getIdPrice(),
                price.getName(),
                price.getValue(),
                price.getCycle(),
                price.getDescription(),
                "",
                "",
                getLocalDateTime(),
                team);

        usersService.saveUser(user);
        team.setFreeSpace((short) (team.getFreeSpace() + (short) 1));
        teamsService.saveTeam(team);

//        return redirectedAttendanceList;
    }


    // TODO: 13.12.2021 usunac brak grupy
    @ResponseBody
    @GetMapping("/getUsersByGroupsId")
    public List<Users> getUsersByGroupsId(String groupId, @AuthenticationPrincipal AppCompany appCompany) {

        if (groupId.equals("all")) {
            return usersService.findAllUsersByAppCompanyId(String.valueOf(appCompany.getIdCompany()));
        }
        else if(groupId.equals("none")){
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

    // TODO: 13.12.2021  Get users by date
    @ResponseBody
    @GetMapping("/getUsersByDates")
    public List<Users> getUsersByDates(@AuthenticationPrincipal AppCompany appCompany) {


        return Collections.emptyList();
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
    @GetMapping("/getTeamsAndPrices")
    public  List <TermsPricesTeams> getTeamsAndPrices(@AuthenticationPrincipal AppCompany appCompany){


        List <TermsPricesTeams> termsPricesTeams = new ArrayList<>();
        List<Teams> teams = teamsService.findAllByCompany(appCompany);

        for (Teams team : teams) {
            List<Prices> prices = pricesService.findAllPricesByTeam(team);
            termsPricesTeams.add(new TermsPricesTeams(prices, team));
        }

        return termsPricesTeams;
    }

    @ResponseBody
    @GetMapping("/getSpecifiedGroupInformation")
    public TermsPricesTeams getTermsPricesTeamsByGroupId(String groupId) {
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
    @ResponseBody
    public String addPrice(
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
            usersService.updateAllUsersByPrice(price);
            return "Cena została zmodyfikowana!";
        } else {
            Prices price = new Prices(appCompany, priceName, priceValue, priceCycle, priceDescription);
            pricesService.addPrices(price);
            return "Nowa cena została zapisana!";
        }


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
    @ResponseBody
    public String deleteGroup(String groupId) {
        String msg = "";
        try {
            Long id = Long.valueOf(groupId);
            teamsPricesService.deleteGroupFromGP(id);
             msg = teamsService.deleteGroupById(id);
             return msg;
        } catch (NumberFormatException ex) {
            LOGGER.error(ex.getMessage());
            LOGGER.error("Nie zaznaczono zadnej opcji!");
           return msg;
        }
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
// TODO: 08.12.2021 Update tabelki GP
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
            teamsPricesService.deleteGroupFromGP(team.getIdTeam());
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

//    @PostMapping("postDates")
//    @ResponseBody
//    public String deleteGroup(String groupId) {
//        String msg = "";
//        try {
//            Long id = Long.valueOf(groupId);
//            teamsPricesService.deleteGroupFromGP(id);
//            msg = teamsService.deleteGroupById(id);
//            return msg;
//        } catch (NumberFormatException ex) {
//            LOGGER.error(ex.getMessage());
//            LOGGER.error("Nie zaznaczono zadnej opcji!");
//            return msg;
//        }
//    }
}
