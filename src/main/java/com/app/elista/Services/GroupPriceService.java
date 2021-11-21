package com.app.elista.Services;

import com.app.elista.model.*;
import com.app.elista.repositories.GroupsPricesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class GroupPriceService {
    @Autowired
    GroupsPricesRepository groupsPricesRepository ;

    public GroupPriceService(GroupsPricesRepository groupsPricesRepository) {
        this.groupsPricesRepository = groupsPricesRepository;
    }

    public void insert(Teams team, List<Prices> prices) {
        List<GroupsPrices> groupsPricesList = new ArrayList<>();
        for (Prices price : prices) {

            GroupsPrices groupsPrices = new GroupsPrices(price, team);
            groupsPricesList.add(groupsPrices);
        }

        groupsPricesRepository.saveAll(groupsPricesList);

    }
}
