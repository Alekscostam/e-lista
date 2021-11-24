package com.app.elista.Services;

import com.app.elista.model.Prices;
import com.app.elista.model.Teams;
import com.app.elista.repositories.PricesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PriceService {
@Autowired
    PricesRepository pricesRepository;
        JdbcTemplate jdbcTemplate;

    public PriceService(PricesRepository pricesRepository, JdbcTemplate jdbcTemplate) {
        this.pricesRepository = pricesRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addPrices(Prices price) {
        pricesRepository.save(price);
    }

    public List<Prices> findAll() {
       return pricesRepository.findAll();
    }

    public List<Prices> findAllByPriceIds(List<String> listsPriceIds) {
        // TODO: 16.11.2021 Zabezpiczenie na nieznalezioną cene
        List<Prices> pricesList = new ArrayList<>();
        for (String listsPriceId : listsPriceIds) {
           Prices prices =  pricesRepository.findById(Long.valueOf(listsPriceId)).get();
            pricesList.add(prices);
        }

        return pricesList;
    }

    public List<Prices> findAllByAppCompanyId(UUID idCompany) {

        String sql = "SELECT p.id_price, p.name,p.value, p.cycle, p.description, gp.id_group_price, gp.id_team FROM  groups_prices gp LEFT  JOIN  prices p on gp.id_price = p.id_price WHERE id_company='"+idCompany+"';";

        return jdbcTemplate.query(
                sql,
                new BeanPropertyRowMapper(Prices.class));
    }
}
