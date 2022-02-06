package com.app.elista.Controller;

import com.app.elista.Services.DatesService;
import com.app.elista.Services.additionalMethods.HelperMethods;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class HomeControllerTest {

    @Test
    public void home() throws ParseException {

//        new Data
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        System.out.println(date);

//        sending data
        String firstDate = "01-03-2021";

        Date result = new SimpleDateFormat("dd-MM-yyyy").parse(firstDate);

        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MMM-yyyy");
        String formatDate = dateFormatter.format(result);
        System.out.println(result);


        if(date.compareTo(result) > 0) {
            System.out.println("Date 1 occurs after Date 2");
        } else if(date.compareTo(result)  < 0) {
            System.out.println("Date 1 occurs before Date 2");
        } else if(date.compareTo(result)  == 0) {
            System.out.println("Both dates are equal");
        }
    }


    @Test
    public void findDay(){

//String stringWithDates, Integer cycle
        LocalDate today = LocalDate.now();
        Map<LocalDate,String>  ld= new HashMap<>();
        String example =  "NiedzielaasdashWtorek:sad";



        for (int i = 0; i < 7; i++) {
            LocalDate ninetyDaysLater = today.plusDays(i);
            String dayOfWeek = ninetyDaysLater.getDayOfWeek().toString();
            dayOfWeek = dayOfWeek.substring(0,1).toUpperCase()+dayOfWeek.substring(1).toLowerCase();
            String dayName = HelperMethods.formatDayWeekName(dayOfWeek);

            if(example.contains(dayName)){
                ld.put(ninetyDaysLater,dayName);

            }
        }


        LocalDate firstDate = null;
        for (Map.Entry<LocalDate, String> entry : ld.entrySet()) {
            firstDate = entry.getKey();
        }

        String dayFirst="";
        if (firstDate.getDayOfMonth()<10)
             dayFirst = "0" + firstDate.getDayOfMonth();
        else
             dayFirst =String.valueOf(firstDate.getDayOfMonth());


        String monthFirst="";
        if (firstDate.getMonthValue()<10)
            monthFirst = "0" + firstDate.getMonthValue();
        else
            monthFirst =String.valueOf(firstDate.getMonthValue());

        int yearFirst = firstDate.getYear();


        String firstDateConverted = dayFirst+"-"+monthFirst+"-"+ yearFirst;


        LocalDate lastDate = firstDate.plusDays(60);


        String dayLast="";
        if (lastDate.getDayOfMonth()<10)
            dayLast = "0" + lastDate.getDayOfMonth();
        else
            dayLast =String.valueOf(lastDate.getDayOfMonth());


        String monthLast="";
        if (lastDate.getMonthValue()<10)
            monthLast = "0" + lastDate.getMonthValue();
        else
            monthLast =String.valueOf(lastDate.getMonthValue());


        int yearLast = lastDate.getYear();

        String lastDateConverted = dayLast+"-"+monthLast+"-"+ yearLast;



    }
}