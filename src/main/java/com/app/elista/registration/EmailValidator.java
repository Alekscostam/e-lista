package com.app.elista.registration;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.Predicate;

@Service
public class EmailValidator implements Predicate<String> {






    @Override
    public boolean test(String s) {
        return true;
    }
}
