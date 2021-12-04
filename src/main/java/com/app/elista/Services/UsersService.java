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
            if(user.getIdUser()==null)
            {
                LOGGER.info("Zapisano nowego użytkownika");
            }
            else   {
                LOGGER.info("Zmodyfikowano istniejącego użytkownika");
            }

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
    public List<Users> findAllUsersByGroupId(String groupId) {
        
        return null;
    }
    public List<Users> findAllUsersByAppCompanyId(String appCompanyId) {

        return null;
    }

}
