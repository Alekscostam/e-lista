package com.app.elista.Services;

import com.app.elista.Services.additionalMethods.HelperMethods;
import com.app.elista.appcompany.AppCompany;
import com.app.elista.model.Prices;
import com.app.elista.model.Teams;
import com.app.elista.model.Users;
import com.app.elista.repositories.UsersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class UsersService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UsersService.class);

    @Autowired
    private UsersRepository usersRepository;

    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

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

            Long id = Long.valueOf(userId);
            usersRepository.deleteById(id);
            LOGGER.info("usunięto użytkownika");

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


    public List<Users> findAllUsers() {
        return usersRepository.findAll();
    }

    public List<Users> findAllUsersByGroupId(String groupId) {

        List<Users> users = usersRepository.findAll().stream()
                .filter(u -> u.getTeams().getIdTeam().equals(Long.valueOf(groupId)))
                .collect(Collectors.toList());
        setNullForAppCompany(users);

        LOGGER.info("termsPricesTeamsFiltered: w findAllUsersBygroupdid" + users.toString());
        return users;
    }

    public List<Users> findAllUsersByGroupsId(List<String> groupsId) {
        List<Users> users = new ArrayList<>();

        for (String s : groupsId) {
            users.addAll(findAllUsersByGroupId(s));
        }
        return users;
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

    }

    public List<Users> deleteGroupById(Long groupId) {
        List<Users> allUsers = findAllUsers();

        List<Users> collect = allUsers.stream().filter(users -> Objects.equals(users.getTeams().getIdTeam(), groupId)).collect(Collectors.toList());

        return collect;
    }

    public void updateAllUsersByPrice(Prices price, AppCompany appCompany) {

        List<Users> allUsersFilteredByCompany = usersRepository.findAll().stream()
                .filter(user -> user.getAppCompany().equals(appCompany))
                .collect(Collectors.toList());

        for (Users user : allUsersFilteredByCompany) {
            try{
            if (user.getIndividualPriceId().equals(price.getIdPrice())) {
                List<String> dataFromDataTo = HelperMethods.dataFromDataTo(user.getTeams().getTerms(), (int) price.getCycle());
                user.setCurrentPaymentDate(dataFromDataTo.get(0));
                user.setNextPaymentDate(dataFromDataTo.get(1));
                user.setIndividualPriceName(price.getName());
                user.setIndividualPriceValue(price.getValue());
                user.setIndividualPriceCycle(price.getCycle());
                user.setIndividualPriceDesc(price.getDescription());
                LOGGER.info("Modyfikacja użytkownika!");
            }}
            catch(NullPointerException npe){
                LOGGER.info("użytkownik ma cene indywidualną!");
            }
        }
        usersRepository.saveAll(allUsersFilteredByCompany);
        LOGGER.info("Cena indywidualna u użytkowników została zmodyfikowana");
    }

    public List<Users> findAllUsersByUserIds(List<String> allValues) {

        List<Long> collect = allValues.stream().map(Long::valueOf).collect(Collectors.toList());

        return usersRepository.findAllById(collect);
    }

    public List<Users> findAllUsersByGroupIdWithAppCompany(String groupId) {
        List<Users> users = usersRepository.findAll().stream()
                .filter(u -> u.getTeams().getIdTeam().equals(Long.valueOf(groupId)))
                .collect(Collectors.toList());


        return users;
    }
}
