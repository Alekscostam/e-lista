package com.app.elista.Services;

import com.app.elista.Services.additionalMethods.HelperMethods;
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
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.app.elista.Services.additionalMethods.HelperMethods.*;

@Service
public class DatesService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DatesService.class);
    ;

    @Autowired
    DatesRepository datesRepository;
    @Autowired
    DatesForGroupsService datesForGroupsService;

    public DatesService(DatesRepository datesRepository, DatesForGroupsService datesForGroupsService) {

        this.datesRepository = datesRepository;
        this.datesForGroupsService = datesForGroupsService;
    }

    public boolean checkDatesExistsFroTeam(Teams teams) {

        return false;
    }

    public Dates saveDate(String dates) {

        Optional<Dates> first = datesRepository.findAll().stream().filter(date -> date.getDatesGroup().equals(dates)).findFirst();
        return first.orElseGet(() -> datesRepository.save(new Dates(dates)));
    }

    public void saveDates(List<String> convertedDates) {

        List<Dates> datesToSave = new ArrayList<>();
        for (int i = 0; i < convertedDates.size(); i++) {
            int finalI = i;
            Optional<Dates> first = datesRepository.findAll().stream().filter(date -> date.getDatesGroup().equals(convertedDates.get(finalI))).findFirst();
            if (first.isPresent()) {
                datesToSave.add(first.get());
            } else {
                datesToSave.add(new Dates(convertedDates.get(finalI)));
            }
        }

        datesRepository.saveAll(datesToSave);

    }

    public List<Dates> findAllDates() {

        return datesRepository.findAll();
    }

    ;

    public void updatesDates(List<Dates> allDates) throws ParseException {


        Optional<Dates> first = allDates.stream().findFirst();
        Dates dates = allDates.get(allDates.size() - 1);
        Date objectToDate = new SimpleDateFormat("dd-MM-yyyy").parse(dates.getDatesGroup());
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDateTime ldt = LocalDateTime.ofInstant(objectToDate.toInstant(), ZoneId.systemDefault()).plusDays(1);
        String format = dtf.format(ldt);

        datesForGroupsService.deleteFromDatesAndGroupsByIdDates(first.get().getIdDates(), dates.getIdDates());

        datesRepository.delete(first.get());
        datesRepository.save(new Dates(format));

    }

    public Dates saveOrGetDateByLdt(String dateForCheck) {

        List<Dates> allDates = findAllDates();
        Optional<Dates> first = allDates.stream().filter(dates -> dates.getDatesGroup().equals(dateForCheck)).findFirst();

        return first.orElseGet(() -> datesRepository.save(new Dates(dateForCheck)));

    }

    public Dates findDateByDate(String date) {

        Optional<Dates> byDate = datesRepository.findByDate(date);
        return byDate.orElse(null);


    }

    public  Long  findDatesUnderActuallyDateByDataId(Long idDates, List<String> days) throws ParseException {
        Long idsToDelete = 0L;

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();

        Dates dates1 = datesRepository
                .findAll()
                .stream()
                .filter(dates -> dates.getIdDates().equals(idDates))
                .findFirst().get();

        Date result = new SimpleDateFormat("dd-MM-yyyy").parse(dates1.getDatesGroup());

        if(date.compareTo(result) > 0) {

        } else if(date.compareTo(result)  < 0) {
            SimpleDateFormat format = new SimpleDateFormat("EEEE");
            String formatDate = format.format(result);
            String weekName = formatDayWeekName(formatDate);

            if (!(days.contains(weekName))) {
                idsToDelete = idDates;
            }

        } else if(date.compareTo(result)  >= 0) {
        }

        return idsToDelete;

    }
}
