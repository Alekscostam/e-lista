package com.app.elista.Controller;

import com.app.elista.Services.DatesService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class HomeControllerTest {

    @Test
    public void home() throws ParseException {

        String date = "2021-12-08";

        String year = date.substring(0,4);
        String month = date.substring(5,7);
        String day = date.substring(8,10);

        Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(date);

        SimpleDateFormat format = new SimpleDateFormat("EEEE");
        System.out.println(format.format(date1));

    }
}