package com.app.elista.Services;

import com.app.elista.model.Dates;
import com.app.elista.model.Presences;
import com.app.elista.model.Users;
import com.app.elista.repositories.PresencesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PresencesService {

    @Autowired
    PresencesRepository presencesRepository;
    JdbcTemplate jdbcTemplate;

    public PresencesService(PresencesRepository presencesRepository, JdbcTemplate jdbcTemplate) {
        this.presencesRepository = presencesRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    public void savePresences(Long dateId, List<String> presencesUsers, List<Users> users) {

        List<Presences> presences = new ArrayList<>();
        List<Long> convertedPresencesUsersId = presencesUsers.stream().map(Long::valueOf).collect(Collectors.toList());

        // TODO: 14.12.2021 sprawdzic czy update czy save. Jesli update to updetuje tabelke Presences ale tylko kolumne presence

        for (Users user : users) {
            if (presencesUsers.isEmpty()) {
                presences.add(new Presences(false, dateId, user));
            } else {
                boolean result = true;
                for (Long presencesUser : convertedPresencesUsersId) {

                    if (Objects.equals(user.getIdUser(), presencesUser)) {
                        presences.add(new Presences(true, dateId, user));
                        result = false;
                        break;
                    }
                }
                if (result) {
                    presences.add(new Presences(false, dateId, user));
                }
            }
        }

        List<Presences> filteredPresences = new ArrayList<>();

        for (Presences value : presences) {

            Users user = value.getUser();
            Boolean presence = value.getPresence();
            Long idDates = value.getIdDates();
            filteredPresences.add(filteredPresence(user, idDates, presence));
        }
        presencesRepository.saveAll(filteredPresences);
    }

    public Presences filteredPresence(Users user, Long dateId, Boolean presences) {
        Optional<Presences> first = presencesRepository
                .findAll().stream()
                .filter(p -> p.getUser().equals(user))
                .filter(p -> p.getIdDates().equals(dateId)).findFirst();

        if (first.isPresent()) {
            first.get().setPresence(presences);
            return first.get();
        } else {
            return new Presences(presences, dateId, user);
        }

    }

    public void deletePresencesByUserId(String userId) {
        String deleteQuery = "delete from presences where id_user = ?";
        jdbcTemplate.update(deleteQuery, Long.valueOf(userId));
    }

    public List<Presences> findPresencesByDate(Dates date, List<Users> users) {

        List<Presences> presences = presencesRepository.findAll().stream()
                .filter(d -> d.getIdDates().equals(date.getIdDates())).collect(Collectors.toList());

        List<Presences> presencesFiltered = new ArrayList<>();

        for (Users user : users) {
            for (Presences presence : presences) {
                if (user.equals(presence.getUser())) {
                    presencesFiltered.add(presence);
                }
            }
        }
        return presencesFiltered;
    }

    // TODO: 25.12.2021 DOKONCZYC ZAPYTANIE SUUWAJACE IDSY
    public void deletePresencesByUserIdAndIdDates(List<Users> allUsers, List<Long> selectedIds) {

//        String deleteQuery = "delete from presences where id_team = ? and";
//        jdbcTemplate.update(deleteQuery,groupId);
//        LOGGER.info("Usunieto dane z tabeli groups_prices");
        
    }
}


