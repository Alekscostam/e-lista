package com.app.elista.Services;

import com.app.elista.model.Prices;
import com.app.elista.model.Terms;
import com.app.elista.repositories.TermsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TermService {

    TermsRepository termsRepository;

    @Autowired
    public TermService(TermsRepository termsRepository) {
        this.termsRepository = termsRepository;
    }

    public List<Terms> addTerms( List<Terms> termsList) {
        List<Terms> savedTermsList = termsRepository.saveAll(termsList);

        return savedTermsList;
    }


}
