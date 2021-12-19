package com.app.elista.Services;

import com.app.elista.appcompany.AppCompany;
import com.app.elista.model.Dates;
import com.app.elista.model.DatesForGroups;
import com.app.elista.model.Teams;
import com.app.elista.repositories.DatesForGroupsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DatesForGroupsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DatesForGroupsService.class);
    ;

    @Autowired
    DatesForGroupsRepository datesForGroupsRepository;
    JdbcTemplate jdbcTemplate;

    public DatesForGroupsService(DatesForGroupsRepository datesForGroupsRepository,JdbcTemplate jdbcTemplate) {
        this.datesForGroupsRepository = datesForGroupsRepository;
        this.jdbcTemplate = jdbcTemplate;

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

    public List<Teams> findGroupsByDateId(Long idDate, List<Teams> teams) {
        List<DatesForGroups> collect = datesForGroupsRepository
                .findAll()
                .stream()
                .filter(d -> d.getIdDates().equals(idDate)).collect(Collectors.toList());

        List<Teams>  filteredTeams= new ArrayList<>();
        for (Teams team : teams) {
            for (DatesForGroups datesForGroups : collect) {
                if (team.getIdTeam().equals(datesForGroups.getTeams().getIdTeam())) {
                    filteredTeams.add(team);
                    LOGGER.info("Grupy zanlezione");
                }
            }
        }
        return filteredTeams;
    }

    public void deleteFromDatesForGroupsByIdGroup(Long id) {

        String deleteQuery = "delete from dates_for_groups where id_team = ?";
        jdbcTemplate.update(deleteQuery,id);
        LOGGER.info("Usunieto dane z tabeli dates_for_groups");
    }
}
