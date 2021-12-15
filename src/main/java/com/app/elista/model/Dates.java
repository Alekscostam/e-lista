package com.app.elista.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
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
