package com.app.elista.Controller;

import com.app.elista.Services.DatesService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class HomeControllerTest {

    @Autowired
    DatesService datesService;

    @Before
    public void init(DatesService datesService){
        this.datesService = datesService;
//        apiController = new ApiController(pricesService, teamsService, teamsPricesService);
//       listDayForGroup = Arrays.asList("jeden","dwa","trzy");
//        listOfTimes = Arrays.asList("1:1","2:2","3:3");
    }
    @Bean
    @Test
    public void home() {







    }
}