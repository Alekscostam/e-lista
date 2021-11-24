package com.app.elista.model;

import com.app.elista.appcompany.AppCompany;

import javax.persistence.*;
import java.util.Objects;

public class Terms {

    private String dayName;
    private String time;

    public Terms() {
    }

    public Terms(String dayName, String time) {
        this.dayName = dayName;
        this.time = time;

    }


}
