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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static com.app.elista.Services.additionalMethods.HelperMethods.*;

@Controller
@RequestMapping("app/")
public class ApiController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApiController.class);
    private static final String redirectedOptionGroupList = "redirect:/app/optionGroupList";
    private static final String redirectedAttendanceList = "redirect:/app/attendanceList";

    @Autowired
    private final PricesService pricesService;
    private final UsersService usersService;
    private final TeamsService teamsService;
    private final TeamsPricesService teamsPricesService;
    private final DatesService datesService;
    private final DatesForGroupsService datesForGroupsService;
    private final PresencesService presencesService;

    public ApiController(PricesService pricesService, UsersService usersService, TeamsService teamsService, TeamsPricesService teamsPricesService, DatesService datesService, DatesForGroupsService datesForGroupsService, PresencesService presencesService) {
        this.pricesService = pricesService;
        this.usersService = usersService;
        this.teamsService = teamsService;
        this.teamsPricesService = teamsPricesService;
        this.datesService = datesService;
        this.datesForGroupsService = datesForGroupsService;
        this.presencesService = presencesService;
    }

    @GetMapping("calendar")
    public String calendar(@AuthenticationPrincipal AppCompany appCompany) {
        return "calendar";
    }

    @GetMapping("attendanceList")
    public ModelAndView attendanceList(@AuthenticationPrincipal AppCompany appCompany) {
//        String localDateTime = getLocalDateTime();
//        String actuallyDayWeekText = getActuallyDayWeekText();
//        setDates(appCompany, localDateTime,actuallyDayWeekText);

        List<Teams> teamIdsAndTeamNamesByUUID = teamsService.findTeamIdsAndTeamNamesByUUID(appCompany.getIdCompany());
        ModelAndView mav = new ModelAndView("attendanceList");
        mav.addObject("teams", teamIdsAndTeamNamesByUUID);

        return mav;
    }

    private void setDates(AppCompany appCompany, String dateForCheck, String weekName) {

        Dates date = datesService.saveOrGetDateByLdt(dateForCheck);

        List<Teams> allTeams = teamsService.findAllByCompanyWithoutAppCompanyReset(appCompany);
        List<TermsPricesTeams> termsPricesTeams = new ArrayList<>();

        for (Teams allTeam : allTeams) {
            List<String> terms = teamsService.getTermsForTeams(allTeam);
            termsPricesTeams.add(new TermsPricesTeams(allTeam, terms));
        }
        for (TermsPricesTeams termsPricesTeam : termsPricesTeams) {
            for (int i1 = 0; i1 < termsPricesTeam.getTerms().size(); i1++) {
                if (termsPricesTeam.getTerms().get(i1).contains(weekName)) {
                    Teams teams = termsPricesTeam.getTeam();
                    datesForGroupsService.saveDatesAndGroups(teams, date);
                    System.out.println("was saved ");
                    System.out.println(date.getIdDates());
                    System.out.println(dateForCheck);
                }
            }
        }
    }


    @ResponseBody
    @PostMapping("postDates")
    public void postDates(String date, @AuthenticationPrincipal AppCompany appCompany) {
        String dateChanged = changeFormatDate(date);
        String dayWeekName = "";
        try {
            dayWeekName = getDayWeekName(date);

            System.out.println("HERE1");
            Dates dateFind = datesService.saveOrGetDateByLdt(dateChanged);
            System.out.println(dateFind);
            System.out.println("dayWeekName: " + dayWeekName);
//            datesForGroupsService.postToDatesForGroups(appCompany, dateChanged, dayWeekName, dateFind.getIdDates());
            setDates(appCompany,date,dayWeekName);

        } catch (ParseException parseException) {
            LOGGER.error(parseException.getMessage());
            LOGGER.error("Błąd przy zapisie daty ");
        }
    }


    @ResponseBody
    @GetMapping("/getUsersByDate")
    public List<Users> getUsersByDate(String date, @AuthenticationPrincipal AppCompany appCompany) {

        String dateChanged = changeFormatDate(date);
        String dayWeekName = "";

        try {
            dayWeekName = getDayWeekName(date);
        } catch (ParseException parseException) {
            LOGGER.error(parseException.getMessage());

        }
        List<Teams> allTeams = teamsService.findAllByCompanyWithoutAppCompanyReset(appCompany);
        Dates dateByDate = datesService.findDateByDate(dateChanged);


        List<Teams> filteredAllTeams = datesForGroupsService.findGroupsByDateId(dateByDate.getIdDates(),allTeams);

        System.out.println("groupIdsByDateId: " + filteredAllTeams.toString());

        List<Users> users = new ArrayList<>();

        if (!filteredAllTeams.isEmpty()) {

            for (Teams team : filteredAllTeams) {
                List<Users> allUsersByGroupId = usersService.findAllUsersByGroupId(String.valueOf(team.getIdTeam()));
                users.addAll(allUsersByGroupId);
            }
        }

        return users;

    }

    public String getActuallyDayWeekText() {
        Date date = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        String dayWeekText = new SimpleDateFormat("EEEE").format(date);

        return dayWeekText.substring(0, 1).toUpperCase() + dayWeekText.substring(1);
    }

    public String getDayWeekName(String date) throws ParseException {

        Date result = new SimpleDateFormat("yyyy-MM-dd").parse(date);

        SimpleDateFormat format = new SimpleDateFormat("EEEE");
        String formatDate = format.format(result);

        System.out.println("formatDate:" + formatDate);
        switch (formatDate) {
            case "Monday":
                formatDate = "Poniedziałek";
                break;
            case "Tuesday":
                formatDate = "Wtorek";
                break;
            case "Wednesday":
                formatDate = "Środa";
                break;
            case "Thursday":
                formatDate = "Czwartek";
                break;
            case "Friday":
                formatDate = "Piątek";
                break;
            case "Saturday":
                formatDate = "Sobota";
                break;
            case "Sunday":
                formatDate = "Niedziela";
                break;
            default:
                formatDate = formatDate.substring(0, 1).toUpperCase() + formatDate.substring(1);
                break;
        }

        System.out.println("formatDate2:" + formatDate);
        return formatDate;
    }

    public String changeFormatDate(String date) {
        String year = date.substring(0, 4);
        String month = date.substring(5, 7);
        String day = date.substring(8, 10);

        return day + "-" + month + "-" + year;
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
    public List<AllInfo> getGroups(@AuthenticationPrincipal AppCompany appCompany) {
        List<AllInfo> allByUUID = teamsService.findAllInfoByAppCompanyUUID(appCompany.getIdCompany());
        return allByUUID;
    }

    @ResponseBody
    @GetMapping("getPricesId")
    public List<String> findInformationAboutGroupEditable(@AuthenticationPrincipal AppCompany appCompany, String groupId) {
        List<String> idPricesByIdTeam = teamsPricesService.findIdPricesByIdTeam(groupId);

        return idPricesByIdTeam;
    }


    @ResponseBody
    @GetMapping("getPrices")
    public List<Prices> getPrices(@AuthenticationPrincipal AppCompany appCompany) {

        List<Prices> all = pricesService.findAllPricesByAppCompanyId(appCompany.getIdCompany());

        return all;
    }

    @PostMapping("optionGroupList")
    public String postGroup(String imie) {
        return "optionGroupList";
    }

    @ResponseBody
    @PostMapping("postUser")
    public void postUser(String groupId, String priceId, String userName, String userSurname, String userPhone, String userEmail, String userOfAge, @AuthenticationPrincipal AppCompany appCompany) {
        Prices price = new Prices();
        if (!priceId.equals("undefined")) {
            price = pricesService.findByPriceId(priceId);
        } else {
            price.setIdPrice(null);
            price.setCycle((short) 0);
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

        Users user = new Users(appCompany, userName, userSurname, Integer.valueOf(userPhone), userEmail, adult, price.getIdPrice(), price.getName(), price.getValue(), price.getCycle(), price.getDescription(), "", "", getLocalDateTime(), team);

        usersService.saveUser(user);
        team.setFreeSpace((short) (team.getFreeSpace() + (short) 1));
        teamsService.saveTeam(team);

    }

    // TODO: 13.12.2021 usunac brak grupy
    @ResponseBody
    @GetMapping("/getUsersByGroupsId")
    public List<Users> getUsersByGroupsId(String groupId, @AuthenticationPrincipal AppCompany appCompany) {

        if (groupId.equals("all")) {
            return usersService.findAllUsersByAppCompanyId(String.valueOf(appCompany.getIdCompany()));
        } else if (groupId.equals("none")) {
            return Collections.emptyList();
        } else {
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

        presencesService.deletePresencesByUserId(userId);
        usersService.deleteUser(userId);
        Teams team = teamsService.findTeamById(groupId);
        team.setFreeSpace((short) (team.getFreeSpace() - (short) 1));
        teamsService.saveTeam(team);

    }

    @ResponseBody
    @GetMapping("/getTeamsAndPrices")
    public List<TermsPricesTeams> getTeamsAndPrices(@AuthenticationPrincipal AppCompany appCompany) {


        List<TermsPricesTeams> termsPricesTeams = new ArrayList<>();
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

    @PostMapping("postPresences")
    @ResponseBody
    public String postPresences(String checkedValue, String allValue, String date, @AuthenticationPrincipal AppCompany appCompany) {

        if (date.equals("")) {
            return "Prosze wybrać datę!";

        } else {
            String changedDate = changeFormatDate(date);
            List<String> checkedValues = new ArrayList<>();
            if (checkedValue.equals("")) {
                checkedValues = new ArrayList<>(checkedValues);

            } else {
                checkedValues = Arrays.stream(checkedValue.substring(1).split(" ")).collect(Collectors.toList());

            }
            List<String> allValues = Arrays.stream(allValue.substring(1).split(" ")).collect(Collectors.toList());
            List<Users> allUsersByUserIds = usersService.findAllUsersByUserIds(allValues);


            Dates foundDate = datesService.saveDate(changedDate);
            presencesService.savePresences(foundDate.getIdDates(), checkedValues, allUsersByUserIds);

            return "zapisano";
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
    public ModelAndView postGroup(@AuthenticationPrincipal AppCompany appCompany, String groupId, String groupName, String groupPlace, String groupSize, String groupLeader, String groupDataFrom, String groupDataTo, String groupDescription, String priceIds, String groupDayFor, String groupTimeFrom, String groupTimeTo, String groupColor, String groupFirstFree) {

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
            team = new Teams(groupName, groupLeader, groupPlace, groupDataFrom, groupDataTo, (short) 0, Short.valueOf(groupSize), groupColor, resultFirstFree, groupDescription, addedTerms, appCompany);
        }

        team = teamsService.saveTeam(team);
        if (!listsPriceIds.isEmpty()) {
            List<Prices> allPricesByPriceIds = pricesService.findAllByPriceIds(listsPriceIds);
            teamsPricesService.deleteGroupFromGP(team.getIdTeam());
            teamsPricesService.insertToGP(team, allPricesByPriceIds);
        }
        return new ModelAndView("redirect:/app/optionGroupList");
    }


}
