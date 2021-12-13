package com.app.elista;

import com.app.elista.Services.DatesForGroupsService;
import com.app.elista.Services.DatesService;
import com.app.elista.Services.PricesService;
import com.app.elista.model.Dates;
import com.app.elista.repositories.DatesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SpringBootApplication
public class EListaApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(EListaApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(EListaApplication.class, args);
    }


    @Bean
    public CommandLineRunner mappingDemo(DatesService datesService, DatesForGroupsService datesForGroupsService) {

        return args -> {

            List<Dates> allDates = datesService.findAllDates();

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDateTime now = LocalDateTime.now();

            String formatDayToday = dtf.format(now);

            List<LocalDateTime> localDateTimes = new ArrayList<>();

            int maxDaysBackwards = 301;
            int maxDaysForwards = 62;
            int todayIndex = allDates.stream().map(Dates::getDatesGroup).collect(Collectors.toList()).indexOf(formatDayToday);

            if (maxDaysBackwards - todayIndex <= 1) {
                try{
                    datesService.updatesDates(allDates);
                    LOGGER.info("zmodyfikowano daty");
                }catch(Exception exception){
                    LOGGER.error(exception.getMessage());
                    LOGGER.error("Coś poszło nie tak przy updaejtowaniu dat");
                }

            } else {
                LOGGER.info("Data została juz dzisiaj zmodyfikowana");

            }
        };
    }


}