package com.app.elista.Controller;

import com.app.elista.Services.DatesService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
}