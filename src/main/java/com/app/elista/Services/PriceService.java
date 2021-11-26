package com.app.elista.Services;

import com.app.elista.model.Prices;
import com.app.elista.model.Teams;
import com.app.elista.repositories.PricesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PriceService {
    private static final Logger LOGGER = LoggerFactory.getLogger(GroupPriceService.class);;

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

        String sql = "SELECT id_price, name, value, cycle, description FROM prices  WHERE id_company='"+idCompany+"';";

        return jdbcTemplate.query(
                sql,
                new BeanPropertyRowMapper(Prices.class));
    }

    public void deletePriceById(Long priceId) {
            try {
                pricesRepository.deleteById(priceId);
            }catch (Exception ex){
                LOGGER.error(ex.getMessage(),"Something goes wrong" );
            }


    }
}
