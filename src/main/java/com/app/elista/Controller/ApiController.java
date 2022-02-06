package com.app.elista.Controller;

import com.app.elista.Services.*;
import com.app.elista.Services.additionalMethods.HelperMethods;
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
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static com.app.elista.Services.additionalMethods.HelperMethods.*;

@Controller
@RequestMapping("app/")
public class ApiController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApiController.class);
    private static final String redirectedOptionGroupList = "redirect:/app/optionGroupList";
    private static final String redirectedAttendanceList = "redirect:/app/attendanceList";
    private static final String deleteDataFromAnotherGroups = "Usunięto dane z powiązanych tabel";

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
            Dates dateFind = datesService.saveOrGetDateByLdt(dateChanged);
            setDates(appCompany, dateChanged, dayWeekName);

        } catch (ParseException parseException) {
            LOGGER.error(parseException.getMessage());
            LOGGER.error("Błąd przy zapisie daty ");
        }
    }


    @ResponseBody
    @GetMapping("/getUsersByDate")
    public List<Presences> getUsersByDate(String date, @AuthenticationPrincipal AppCompany appCompany) {

        String dateChanged = changeFormatDate(date);

        List<Teams> allTeams = teamsService.findAllByCompanyWithoutAppCompanyReset(appCompany);
        Dates dateByDate = datesService.findDateByDate(dateChanged);

        List<Teams> filteredAllTeams = datesForGroupsService.findGroupsByDateId(dateByDate.getIdDates(), allTeams);

        List<Users> users = new ArrayList<>();

        if (!filteredAllTeams.isEmpty()) {

            for (Teams team : filteredAllTeams) {
                List<Users> allUsersByGroupId = usersService.findAllUsersByGroupId(String.valueOf(team.getIdTeam()));
                users.addAll(allUsersByGroupId);
            }
        }

        List<Presences> presences = presencesService.findPresencesByDate(dateByDate, users);

        if (presences.isEmpty()) {
            presences.clear();
            for (Users user : users) {
                presences.add(new Presences(user, false));
            }
        }
        return presences;
    }

    public String getActuallyDayWeekText() {
        Date date = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        String dayWeekText = new SimpleDateFormat("EEEE").format(date);

        return dayWeekText.substring(0, 1).toUpperCase() + dayWeekText.substring(1);
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
        List<Prices> allPrices = pricesService.findAllPricesByAppCompany(appCompany);
        ModelAndView mav = new ModelAndView("optionGroupList");
        mav.addObject("prices", allPrices);
        mav.addObject("allInfos", new ArrayList<>());
        return mav;
    }

    @GetMapping("getGroups")
    @ResponseBody
    public List<AllInfo> getGroups(@AuthenticationPrincipal AppCompany appCompany) {
        List<AllInfo> allByUUID = teamsService.findAllInfoByAppCompanyUUID(appCompany.getIdCompany());
        List<Prices> allPricesByAppCompany = pricesService.findAllPricesByAppCompany(appCompany);
        allPricesByAppCompany.forEach(p -> p.setAppCompany(null));
        for (AllInfo allInfo : allByUUID) {
            allInfo.setCheckedPrices(pricesService.findAllIdsPricesByIdTeam(allInfo.getTeams().getIdTeam()));
            allInfo.setAllPrices(allPricesByAppCompany);
        }
        return allByUUID;
    }

    @ResponseBody
    @GetMapping("getPrices")
    public List<Prices> getPrices(@AuthenticationPrincipal AppCompany appCompany) {

        List<Prices> all = pricesService.findAllPricesByAppCompanyId(appCompany.getIdCompany());

        return all;
    }


    @GetMapping("priceList")
    public ModelAndView priceList(@AuthenticationPrincipal AppCompany appCompany) {

        ModelAndView mav = new ModelAndView("priceList");
        List<Prices> allPrices = pricesService.findAllPricesByAppCompanyId(appCompany.getIdCompany());
        mav.addObject("prices", allPrices);

        return mav;
    }


    @ResponseBody
    @GetMapping("getPricesId")
    public List<String> findInformationAboutGroupEditable(@AuthenticationPrincipal AppCompany appCompany, String groupId) {
        List<String> idPricesByIdTeam = teamsPricesService.findIdPricesByIdTeam(groupId);

        return idPricesByIdTeam;
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

        List<String> dataFromDataTo = dataFromDataTo(team.getTerms(), (int) price.getCycle());

        boolean adult;
        adult = Objects.equals(userOfAge, "true");

        Users user = new Users(appCompany, userName, userSurname, Integer.valueOf(userPhone), userEmail, adult, price.getIdPrice(), price.getName(), price.getValue(), price.getCycle(), price.getDescription(),dataFromDataTo.get(0) , dataFromDataTo.get(1), getLocalDateTime(), team);

        usersService.saveUser(user);
        team.setFreeSpace((short) (team.getFreeSpace() + (short) 1));
        teamsService.saveTeam(team);

    }
    @ResponseBody
    @PostMapping("editUser")
    public void editUser(String groupId, String priceId, String userName, String userSurname, String userPhone, String userEmail, String userOfAge, @AuthenticationPrincipal AppCompany appCompany) {



    }

    // TODO: 13.12.2021 usunac brak grupy
    @ResponseBody
    @GetMapping("/getUsersByGroupsId")
    public List<Presences> getUsersByGroupsId(String groupId, @AuthenticationPrincipal AppCompany appCompany) {

        List<Presences> presences = new ArrayList<>();

        if (groupId.equals("all")) {
            List<Users> allUsersByAppCompanyId = usersService.findAllUsersByAppCompanyId(String.valueOf(appCompany.getIdCompany()));
            for (Users users : allUsersByAppCompanyId) {
                presences.add(new Presences(users, false));
            }
            return presences;
        } else if (groupId.equals("none")) {
            return Collections.emptyList();
        } else {
            if (!groupId.isEmpty()) {
                List<Users> allUsersByGroupId = usersService.findAllUsersByGroupId(groupId);
                for (Users users : allUsersByGroupId) {
                    presences.add(new Presences(users, false));
                }
                return presences;
            } else {
                List<Users> allUsersWithoutGroups = usersService.findAllUsersWithoutGroups(String.valueOf(appCompany.getIdCompany()));
                for (Users users : allUsersWithoutGroups) {
                    presences.add(new Presences(users, false));
                }
                return presences;
            }
        }
    }

    @ResponseBody
    @PostMapping("/deleteUser")
    public void deleteUser(String userId, String groupId) {

        try{
            usersService.deleteUser(userId);
            LOGGER.info("Usunięto użytkownika");
            Teams team = teamsService.findTeamById(groupId);
            team.setFreeSpace((short) (team.getFreeSpace() - (short) 1));
            teamsService.saveTeam(team);
            LOGGER.info("Zmodyfikowano grupę");
        }
        catch (Exception ex){
            presencesService.deletePresencesByUserId(userId);
            LOGGER.info( deleteDataFromAnotherGroups);
            usersService.deleteUser(userId);
            LOGGER.info("Usunięto użytkownika");
            Teams team = teamsService.findTeamById(groupId);
            team.setFreeSpace((short) (team.getFreeSpace() - (short) 1));
            teamsService.saveTeam(team);
            LOGGER.info("Zmodyfikowano grupę");
        }


    }

    @ResponseBody
    @GetMapping("/getTeamsAndPrices")
    public List<TermsPricesTeams> getTeamsAndPrices(@AuthenticationPrincipal AppCompany appCompany) {


        List<TermsPricesTeams> termsPricesTeams = new ArrayList<>();
        List<Teams> teams = teamsService.findAllByCompany(appCompany);

        for (Teams team : teams) {
            List<Prices> prices = pricesService.findAllIdsPricesByIdTeam(team.getIdTeam());
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
            List<Prices> allPrices = pricesService.findAllIdsPricesByIdTeam(team.getIdTeam());
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
            usersService.updateAllUsersByPrice(price, appCompany);
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

            return "Zapisano!";
        }

    }

    @PostMapping("deletePrice")
    public ModelAndView deletePrice(String priceId) {
        Long id = Long.valueOf(priceId);
        try {
            pricesService.deletePriceById(id);
        } catch (Exception ex) {
            teamsPricesService.deletePriceFromGP(id);
            LOGGER.error(ex.getMessage(), deleteDataFromAnotherGroups);
            pricesService.deletePriceById(id);
            LOGGER.error(ex.getMessage(), "Usunięto cenę");
        }
        return new ModelAndView(redirectedOptionGroupList);
    }

    @PostMapping("deleteGroup")
    @ResponseBody
    public String deleteGroup(String groupId) {
        String msg = "";

        Long id = Long.valueOf(groupId);
        try {
            msg = teamsService.deleteGroupById(id);
            LOGGER.info("Usunięto grupę");
            return msg;
        } catch (Exception ex) {
            teamsPricesService.deleteFromGroupsPricesByIdGroup(id);
            datesForGroupsService.deleteFromDatesForGroupsByIdGroup(id);
            LOGGER.info(deleteDataFromAnotherGroups);
            msg = teamsService.deleteGroupById(id);
            LOGGER.info("Usunięto grupę");
            return msg;
        }
    }

    @PostMapping("postGroup")
    public ModelAndView postGroup(@AuthenticationPrincipal AppCompany appCompany, String groupId, String groupName, String groupPlace, String groupSize, String groupLeader, String groupDataFrom, String groupDataTo, String groupDescription, String priceIds, String groupDayFor,
                                  String groupTimeFromHour,
                                  String groupTimeFromMinute,
                                  String groupTimeToHour,
                                  String groupTimeToMinute,
                                  String groupColor, String groupFirstFree) throws ParseException {

        String[] splitTimeFromHour = groupTimeFromHour.split(",");
        String[] splitTimeFromMinute = groupTimeFromMinute.split(",");
        String[] splitTimeToHour = groupTimeToHour.split(",");
        String[] splitTimeToMinute = groupTimeToMinute.split(",");
        String[] splitDays = groupDayFor.split(",");

        if (groupColor == null) {
            groupColor = "ffffff";
        }

        List<String> listsPriceIds = divideStringToList(priceIds);
        List<String> convertedPriceIds = new ArrayList<>();

        for (String listsPriceId : listsPriceIds) {
            if (listsPriceId.equals("")) {
            } else {
                convertedPriceIds.add(listsPriceId);
            }
        }
        boolean resultFirstFree = Boolean.TRUE;

        if (groupFirstFree == null) {
            resultFirstFree = Boolean.FALSE;
        }

        String addedTerms = "";
        if (!groupDayFor.isEmpty()) {
            addedTerms = splitDaysAndTimes(splitTimeFromHour, splitTimeFromMinute, splitTimeToHour, splitTimeToMinute, splitDays);
        }

        if (groupSize.isEmpty()) {
            groupSize = "0";
        }

        Teams team;

        if (groupId != null && !(groupId.isEmpty())) {
            Teams teamById = teamsService.findTeamById(groupId);
            team = teamById;
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
            List<String> days = splitDays(splitDays);

            List<DatesForGroups> byGroupId = datesForGroupsService.findByGroupId(teamById.getIdTeam());
            List<Long> idDates = byGroupId.stream().map(DatesForGroups::getIdDates).collect(Collectors.toList());
            List<Long> idsToDelete= new ArrayList<>();

            for (Long idDate : idDates) {
                idsToDelete.add(datesService.findDatesUnderActuallyDateByDataId(idDate, days));
            }

            List<DatesForGroups> filteredDates = new ArrayList<>();

            for (DatesForGroups datesForGroups : byGroupId) {
                for (Long aLong : idsToDelete) {
                    if (datesForGroups.getIdDates().equals(aLong)) {
                        filteredDates.add(datesForGroups);
                    }
                }
            }
            List<Long> selectedIds = filteredDates.stream().map(DatesForGroups::getIdDatesForGroups).collect(Collectors.toList());
            List<Long> selectedIdDates = filteredDates.stream().map(DatesForGroups::getIdDates).collect(Collectors.toList());
            datesForGroupsService.deleteFromDatesAndGroups(selectedIds);
            List<Users> allUsers = usersService.findAllUsersByGroupIdWithAppCompany(groupId);
            presencesService.deletePresencesByUserIdAndIdDates(allUsers,selectedIdDates);
            datesForGroupsService.editDatesForGroups(teamById,days);

        } else {
            team = new Teams(groupName, groupLeader, groupPlace, groupDataFrom, groupDataTo, (short) 0, Short.valueOf(groupSize), groupColor, resultFirstFree, groupDescription, addedTerms, appCompany);
        }
        team = teamsService.saveTeam(team);

        if (!convertedPriceIds.isEmpty()){
                List<Prices> allPricesByPriceIds = pricesService.findAllByPriceIds(convertedPriceIds);
                teamsPricesService.deleteFromGroupsPricesByIdGroup(team.getIdTeam());
                teamsPricesService.insertToGP(team, allPricesByPriceIds);
        }else{
            teamsPricesService.deleteFromGroupsPricesByIdGroup(team.getIdTeam());
        }
        return new ModelAndView("redirect:/app/optionGroupList");
    }


    private String splitDaysAndTimes(String[] splitTimeFromHour, String[] splitTimeFromMinute, String[] splitTimeToHour, String[] splitTimeToMinute, String[] splitDays) {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < splitDays.length; i++) {
            if(splitDays[i].contains("brak")){

            }else{
                stringBuilder.append(splitDays[i] + ": " + splitTimeFromHour[i] + ":" + splitTimeFromMinute[i] + " - " + splitTimeToHour[i] + ":" + splitTimeToMinute[i] + ";");
            }
        }
        return stringBuilder.toString();

    }


    private List<String> splitDays(String[] splitDays) {
        List<String>  days = new ArrayList<>();

        for (String splitDay : splitDays) {
            if (splitDay.contains("brak")) {

            } else {
                days.add(splitDay);
            }
        }
        return days;
    }
}


// TODO: 16.12.2021 EDYCJA GRUPY! :)
// TODO: 16.12.2021 Ceny do osobnej zakładki!!! 