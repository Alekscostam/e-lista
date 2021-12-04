package com.app.elista.Controller;

import com.app.elista.Services.*;
import com.app.elista.appcompany.AppCompany;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class ApiControllerTest {

    @Mock
    PricesService pricesService;

    @Mock
    TeamsService teamsService;
    @Mock
    TeamsPricesService teamsPricesService;
    @Mock
    AppCompany appCompany;

    List<String> listDayForGroup;
    List<String> listOfTimes;
    ApiController apiController;


    @Before
    public void init(){
//        apiController = new ApiController(pricesService, teamsService, teamsPricesService);
//       listDayForGroup = Arrays.asList("jeden","dwa","trzy");
//        listOfTimes = Arrays.asList("1:1","2:2","3:3");
    }

    @Test
    public void calendar() {
    }

    @Test
    public void attendanceList() {
    }

    @Test
    public void usersList() {
    }

    @Test
    public void optionUserList() {
    }

    @Test
    public void getGroups() {
    }

    @Test
    public void postGroup() {
    }

    @Test
    public void postPrice() {
    }

    @Test
    public void testPostGroup() {
    }

    @Test
    public void shouldDivideStringToList() {
        String dayForGroup = "Wtorek, Sobota";
        String times = "12:54, 13:20";
        String ids = "1, 2";

        List<String> listDayForGroup = apiController.divideStringToList(dayForGroup);
        List<String> listOfTimes = apiController.divideStringToList(times);
        List<String> listOfIds = apiController.divideStringToList(ids);

        assertEquals("listDayForGroup: ",listDayForGroup.get(0),"Wtorek");
        assertEquals("listOfTimes: ",listOfTimes.get(1),"13:20");
        assertEquals("listOfIds: ",listOfIds.get(0),"1");
        String value = "as";
    }


    @Test
    public void shouldConvertStringListsToTermLists() {
//       String s = apiController.stringListsToString(listDayForGroup, listOfTimes);
//        Terms terms1 = new Terms(listDayForGroup.get(0),listOfTimes.get(0));
//        Terms terms2 = new Terms(listDayForGroup.get(1),listOfTimes.get(1));
//        Terms terms3 = new Terms(listDayForGroup.get(2),listOfTimes.get(2));

//        assertEquals("terms1: ",s.get(0),terms1);
//        assertEquals("terms2: ",s.get(1),terms2);
//        assertEquals("terms3: ",s.get(2),terms3);
    }

    @Test
    public void shouldConvertStringListsToTermListsx() {


    }

}