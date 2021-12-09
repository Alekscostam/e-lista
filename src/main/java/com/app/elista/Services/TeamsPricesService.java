package com.app.elista.Services;

import com.app.elista.model.*;
import com.app.elista.repositories.TeamsPricesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class TeamsPricesService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TeamsPricesService.class);;

    @Autowired
    TeamsPricesRepository teamsPricesRepository;
    JdbcTemplate jdbcTemplate;

    public TeamsPricesService(TeamsPricesRepository teamsPricesRepository, JdbcTemplate jdbcTemplate) {
        this.teamsPricesRepository = teamsPricesRepository;
        this.jdbcTemplate = jdbcTemplate;
    }


    public void insertToGP(Teams team, List<Prices> prices) {
        List<GroupsPrices> teamsPricesList = new ArrayList<>();
        for (Prices price : prices) {

            GroupsPrices groupsPrices = new GroupsPrices(price, team);
            teamsPricesList.add(groupsPrices);
        }

        teamsPricesRepository.saveAll(teamsPricesList);

    }

    public void deleteFromGP(Teams team, List<Prices> prices) {
        List<GroupsPrices> teamsPricesList = new ArrayList<>();
        for (Prices price : prices) {

            GroupsPrices groupsPrices = new GroupsPrices(price, team);
            teamsPricesList.add(groupsPrices);
        }

        teamsPricesRepository.saveAll(teamsPricesList);

    }

    public List <String> findIdPricesByIdTeam(String groupId){

        String sql = "SELECT p.id_price  FROM prices p, groups_prices gp WHERE  p.id_price = gp.id_price AND gp.id_team= '" +Long.valueOf(groupId)  + "';";

        List<String> query = jdbcTemplate.query(sql, (rs, rowNum) -> (
                rs.getString(1)));
        return query;

    }

    public void deletePriceFromGP(Long priceId) {

            String deleteQuery = "delete from groups_prices where id_price = ?";
            jdbcTemplate.update(deleteQuery,priceId);

    }

    public void deleteGroupFromGP(Long groupId) {

        String deleteQuery = "delete from groups_prices where id_team = ?";
        jdbcTemplate.update(deleteQuery,groupId);
        LOGGER.info("Usunieto dane z tabeli groups_prices");

    }
}
