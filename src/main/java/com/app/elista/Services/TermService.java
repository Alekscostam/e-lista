package com.app.elista.Services;

import com.app.elista.model.Prices;
import com.app.elista.model.Terms;
import com.app.elista.repositories.TermsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TermService {



    TermsRepository termsRepository;

    @Autowired
    public TermService(TermsRepository termsRepository) {
        this.termsRepository = termsRepository;
    }

    public List<Terms> addTerms(List<Terms> termsList) {
        List<Terms> savedTermsList = termsRepository.saveAll(termsList);
//        List<Long> idTermsList = savedTermsList.stream().map(a -> a.getIdTerm()).toList();
//        termsRepository.flush();
//        termsRepository.saveAll(termsList);
//        termsRepository.flush();
        return savedTermsList;
    }
     public void findOne(){


     }

}
