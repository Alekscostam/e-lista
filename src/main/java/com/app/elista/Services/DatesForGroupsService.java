package com.app.elista.Services;

import com.app.elista.appcompany.AppCompany;
import com.app.elista.model.Dates;
import com.app.elista.model.DatesForGroups;
import com.app.elista.model.Teams;
import com.app.elista.repositories.DatesForGroupsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DatesForGroupsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DatesForGroupsService.class);
    ;

    @Autowired
    DatesForGroupsRepository datesForGroupsRepository;


    public DatesForGroupsService(DatesForGroupsRepository datesForGroupsRepository) {
        this.datesForGroupsRepository = datesForGroupsRepository;

    }

    public void deleteFromDatesAndGroupsByIdDates(Long idDateFirst, Long idDateLast){

        boolean b = checkIdDateExist(idDateFirst);
        boolean a = checkIdDateExist(idDateLast);
        if (b) {
            datesForGroupsRepository.deleteById(idDateFirst);
        }
        if (a) {
            datesForGroupsRepository.deleteById(idDateLast);
        }
    }

    private boolean checkIdDateExist(Long idDate) {
       return datesForGroupsRepository.existsById(idDate);
    }

    public void saveDatesAndGroups(Teams team, Dates date) {
        Optional<DatesForGroups> first = datesForGroupsRepository
                .findAll()
                .stream()
                .filter(group -> group.getTeams().equals(team))
                .filter(group -> group.getIdDates().equals(date.getIdDates()))
                .findFirst();

        if (!first.isPresent()) {
            datesForGroupsRepository.save(new DatesForGroups(date.getIdDates(),team));
        }

    }

    public List<Teams> findGroupsByDateIdAndAppCompany(Long idDate, AppCompany appCompany) {
        List<Teams> teamsList = datesForGroupsRepository.findGroupsByDateId(idDate).get().stream().filter(team -> team.getAppCompany().equals(appCompany)).collect(Collectors.toList());

        return teamsList;
    }

    public void postToDatesForGroups(AppCompany appCompany, String dateChanged, String dayWeekName, Long dateId) {

        List<DatesForGroups> all = datesForGroupsRepository.findAll();

        List<DatesForGroups> filteredAll = all.stream()
                .filter(app -> app.getTeams().getAppCompany().equals(appCompany))
                .filter(dfg -> dfg.getIdDates().equals(dateId)).collect(Collectors.toList());
        System.out.println(all.toString());

        if (filteredAll.isEmpty())
        {
            System.out.println(filteredAll.isEmpty());
            System.out.println(dayWeekName);
            System.out.println(dateChanged);
            System.out.println(dateId);
            for (int i = 0; i < all.size(); i++) {
                System.out.println(all.get(i).getTeams().getTerms().toString());
                if (all.get(i).getTeams().getTerms().contains(dayWeekName)) {

                     datesForGroupsRepository.save(new DatesForGroups(dateId,all.get(i).getTeams()));
                    LOGGER.error("DODAWANIE TERMINU DO DATE FOR GROUPS ");
                }
            }
        }
        else{

        }


    }
}
