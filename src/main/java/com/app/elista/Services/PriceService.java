package com.app.elista.Services;

import com.app.elista.model.Prices;
import com.app.elista.repositories.PricesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
