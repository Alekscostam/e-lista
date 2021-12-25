package com.app.elista.Services.additionalMethods;

import lombok.NoArgsConstructor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;


public class HelperMethods {

    private HelperMethods() {
    }

    public static List<String> divideStringToList(String elementToDivide) {

        if (elementToDivide != null) {
            String[] splitOne = elementToDivide.replaceAll("\\s+", "").split(",");
            return Arrays.asList(splitOne);

        } else
            return Collections.emptyList();
    }

    public  static String getDayWeekName(String date) throws ParseException {

        Date result = new SimpleDateFormat("yyyy-MM-dd").parse(date);

        SimpleDateFormat format = new SimpleDateFormat("EEEE");
        String formatDate = format.format(result);

       return formatDayWeekName(formatDate);

    }

    public static String formatDayWeekName(String formatDate) {

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

        return formatDate;
    }

    public static String stringListsToString(String dayToDivide, String elementToDivideOne, String elementToDivideTwo) {

        StringBuilder stringBuilder = new StringBuilder();

        List<String> dayNames = divideStringToList(dayToDivide);
        List<String> timesFrom = divideStringToList(elementToDivideOne);
        List<String> timesTo = divideStringToList(elementToDivideTwo);

        for (int i = 0; i < dayNames.size(); i++) {
            stringBuilder.append(dayNames.get(i)).append(": ").append(timesFrom.get(i)).append(" - ").append(timesTo.get(i)).append(";");
        }
        return stringBuilder.toString();
    }

    public  static String getLocalDateTime() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }
}
