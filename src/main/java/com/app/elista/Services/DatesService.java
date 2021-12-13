package com.app.elista.Services;

import com.app.elista.model.Dates;
import com.app.elista.model.Teams;
import com.app.elista.repositories.DatesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DatesService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DatesService.class);;

    @Autowired
    DatesRepository datesRepository;
    @Autowired
    DatesForGroupsService datesForGroupsService;

    public DatesService( DatesRepository datesRepository,DatesForGroupsService datesForGroupsService) {

        this.datesRepository = datesRepository;
        this.datesForGroupsService = datesForGroupsService;
    }

    public boolean checkDatesExistsFroTeam(Teams teams){

        return false;
    }
    public Dates saveDate(String dates){

        Optional<Dates> first = datesRepository.findAll().stream().filter(date -> date.getDatesGroup().equals(dates)).findFirst();
        //Dates save = datesRepository.save(new Dates(dates));
        //return save;
        return first.orElseGet(() -> datesRepository.save(new Dates(dates)));


    }

    public void saveDates(List<String> convertedDates) {

        List<Dates> datesToSave = new ArrayList<>();
        for (int i = 0; i < convertedDates.size(); i++) {
            int finalI = i;
            Optional<Dates> first = datesRepository.findAll().stream().filter(date -> date.getDatesGroup().equals(convertedDates.get(finalI))).findFirst();
            if(first.isPresent()){
                datesToSave.add(first.get());
            }
            else{
                datesToSave.add(new Dates(convertedDates.get(finalI)));
            }
        }

        datesRepository.saveAll(datesToSave);

    }

    public List<Dates> findAllDates(){

        return datesRepository.findAll();
    };

    public void updatesDates(List<Dates> allDates) throws ParseException {


        Optional<Dates> first = allDates.stream().findFirst();
        Dates dates = allDates.get(allDates.size() - 1);
        Date objectToDate=new SimpleDateFormat("dd-MM-yyyy").parse(dates.getDatesGroup());
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDateTime ldt = LocalDateTime.ofInstant(objectToDate.toInstant(), ZoneId.systemDefault()).plusDays(1);
        String format = dtf.format(ldt);

        datesForGroupsService.deleteFromDatesAndGroupsByIdDates(first.get().getIdDates(), dates.getIdDates());

        datesRepository.delete(first.get());
        datesRepository.save(new Dates(format));

    }

    public Dates saveOrGetDateByLdt(String localDateTime) {

        List<Dates> allDates = findAllDates();
        Optional<Dates> first = allDates.stream().filter(dates -> dates.getDatesGroup().equals(localDateTime)).findFirst();

            return first.orElseGet(() -> datesRepository.save(new Dates(localDateTime)));

    }
}
