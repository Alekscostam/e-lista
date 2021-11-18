package com.app.elista.Services;

import com.app.elista.model.Prices;
import com.app.elista.repositories.PricesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PriceService {
@Autowired
    PricesRepository pricesRepository;

    public PriceService(PricesRepository pricesRepository) {
        this.pricesRepository = pricesRepository;
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
}
