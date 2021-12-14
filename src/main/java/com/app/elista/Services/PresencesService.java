package com.app.elista.Services;

import com.app.elista.model.Presences;
import com.app.elista.model.Users;
import com.app.elista.repositories.PresencesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class PresencesService {

    @Autowired
    PresencesRepository presencesRepository;

    public PresencesService(PresencesRepository presencesRepository) {
        this.presencesRepository = presencesRepository;
    }

    public void savePresences(Long dateId, List<String> presencesUsers, List<Users> users) {

        List<Presences> presences = new ArrayList<>();
        List<Long> convertedPresencesUsersId = presencesUsers.stream().map(Long::valueOf).collect(Collectors.toList());

        // TODO: 14.12.2021 sprawdzic czy update czy save. Jesli update to updetuje tabelke Presences ale tylko kolumne presence

        for (Users user : users) {
            for (Long presencesUser : convertedPresencesUsersId) {
                if (Objects.equals(user.getIdUser(), presencesUser)) {
                    presences.add(new Presences(true, dateId, user));
                } else {
                    presences.add(new Presences(false, dateId, user));
                }
            }
        }

        presencesRepository.saveAll(presences);
    }


    // TODO: 14.12.2021 utworzyc usuwanie użytkownika z tabelki preneces w przypadku usunięcia go

}
