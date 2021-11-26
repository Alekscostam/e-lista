package com.app.elista.Services;

import com.app.elista.forgotPass.PasswordResetController;
import com.app.elista.model.*;
import com.app.elista.repositories.GroupsPricesRepository;
import com.sun.org.apache.xpath.internal.operations.Number;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class GroupPriceService {
    private static final Logger LOGGER = LoggerFactory.getLogger(GroupPriceService.class);;

    @Autowired
    GroupsPricesRepository groupsPricesRepository ;
    JdbcTemplate jdbcTemplate;

    public GroupPriceService(GroupsPricesRepository groupsPricesRepository, JdbcTemplate jdbcTemplate) {
        this.groupsPricesRepository = groupsPricesRepository;
        this.jdbcTemplate = jdbcTemplate;
    }


    public void insertToGP(Teams team, List<Prices> prices) {
        List<GroupsPrices> groupsPricesList = new ArrayList<>();
        for (Prices price : prices) {

            GroupsPrices groupsPrices = new GroupsPrices(price, team);
            groupsPricesList.add(groupsPrices);
        }

        groupsPricesRepository.saveAll(groupsPricesList);

    }

    public void deletePriceFromGP(Long priceId) {

            String deleteQuery = "delete from groups_prices where id_price = ?";
            jdbcTemplate.update(deleteQuery,priceId);

    }

    public void deleteGroupFromGP(Long groupId) {

        String deleteQuery = "delete from groups_prices where id_team = ?";
        jdbcTemplate.update(deleteQuery,groupId);

    }
}
