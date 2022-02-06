package com.app.elista.Services.additionalMethods;

import lombok.NoArgsConstructor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


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


    public static List<String> dataFromDataTo(String stringWithDates, Integer cycle){

//String stringWithDates, Integer cycle
        LocalDate today = LocalDate.now();
        Map<LocalDate,String> ld= new HashMap<>();

            List<String> listDataFromDataTo = new ArrayList<>();


        for (int i = 0; i < 7; i++) {
            LocalDate daysLater = today.plusDays(i);
            String dayOfWeek = daysLater.getDayOfWeek().toString();
            dayOfWeek = dayOfWeek.substring(0,1).toUpperCase()+dayOfWeek.substring(1).toLowerCase();
            String dayName = HelperMethods.formatDayWeekName(dayOfWeek);

            if(stringWithDates.contains(dayName)){
                ld.put(daysLater,dayName);

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


        LocalDate lastDate = firstDate.plusDays(cycle);


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

        listDataFromDataTo.add(firstDateConverted);
        listDataFromDataTo.add(lastDateConverted);

        return listDataFromDataTo;
    }
}
