package com.app.elista.Services;

import com.app.elista.appcompany.AppCompany;
import com.app.elista.model.Teams;
import com.app.elista.model.Users;
import com.app.elista.repositories.UsersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UsersService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UsersService.class);

    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Autowired
    UsersRepository usersRepository;

    public Users saveUser(Users user) {
        try {
            usersRepository.save(user);
                LOGGER.info("Zapisano/Zmodyfikowano nowego/istniejącego użytkownika");

        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), "cos poszlo nie tak przy dodawaniu/modyfikowaniu użytkownika");
        }
        return null;
    }

    public Users deleteUser(String userId) {
        try {
            Long id = Long.valueOf(userId);
            usersRepository.deleteById(id);
            LOGGER.info("usunięto użytkownika");
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), "cos poszlo nie tak przy usuwaniu użytkownika");
        }
        return null;
    }

    // TODO: 04.12.2021 here we must
    public List<Users> findAllUsersByGroup(Teams teams) {
//        List<Users> byTeams = usersRepository.findByTeams(teams);

        return null;
    }

    public List<Users> findAllUsersByAppCompanyId(String appCompanyId) {
        List<Users> users = usersRepository.findAll();

        List<Users> collect = users
                .stream()
                .filter(u -> u.getAppCompany().getIdCompany().equals(UUID.fromString(appCompanyId)))
                .collect(Collectors.toList());
        setNullForAppCompany(collect);

        return collect;
    }


    public void findAllUsers() {
        usersRepository.findAll();
    }

    public List<Users> findAllUsersByGroupId(String groupId) {
        List<Users> users = usersRepository.findAll();
        List<Users> collect = users
                .stream()
                .filter(u -> u.getTeams().getIdTeam().equals(Long.valueOf(groupId)))
                .collect(Collectors.toList());
        setNullForAppCompany(collect);
        return collect;

    }

    public List<Users> findAllUsersWithoutGroups(String appCompanyId) {
        List<Users> users = usersRepository.findAll();
        List<Users> collect = users
                .stream()
                .filter(u -> u.getAppCompany().getIdCompany().equals(UUID.fromString(appCompanyId)))
                .filter(u -> u.getTeams().getIdTeam() == null)
                .collect(Collectors.toList());
        setNullForAppCompany(collect);
        return collect;
    }

    public void setNullForAppCompany(List<Users> users) {
        users.forEach(u -> u.setAppCompany(null));
        users.forEach(u -> u.getTeams().setAppCompany(null));
        users.forEach(u -> u.getPrices().setAppCompany(null));
    }
}
