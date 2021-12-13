package com.app.elista.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Dates {



    @SequenceGenerator(
            name = "Dates_sequence",
            sequenceName = "Dates_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "Dates_sequence"
    )
    @Id
    private Long idDates;
    private String datesGroup;

    public Dates() {
    }

    public Long getIdDates() {
        return idDates;
    }

    public void setIdDates(Long idDates) {
        this.idDates = idDates;
    }

    public String getDatesGroup() {
        return datesGroup;
    }

    public void setDatesGroup(String datesGroup) {
        this.datesGroup = datesGroup;
    }

    public Dates(String datesGroup) {
        this.datesGroup = datesGroup;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dates dates = (Dates) o;
        return Objects.equals(idDates, dates.idDates) && Objects.equals(datesGroup, dates.datesGroup);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idDates, datesGroup);
    }

}
