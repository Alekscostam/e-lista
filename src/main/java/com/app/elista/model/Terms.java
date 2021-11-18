package com.app.elista.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Terms {




    @SequenceGenerator(
            name = "terms_sequence",
            sequenceName = "terms_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "terms_sequence"
    )
    private Long idTerm;
    @Column(length = 15)
    private String dayName;
    @Column(length = 5)
    private String time;

    public Terms() {
    }

    public Terms(String dayName, String time) {
        this.dayName = dayName;
        this.time = time;
    }


    public Long getIdTerm() {
        return idTerm;
    }

    public void setIdTerm(Long idTerm) {
        this.idTerm = idTerm;
    }

    public String getDayName() {
        return dayName;
    }

    public void setDayName(String dayName) {
        this.dayName = dayName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
//    @ManyToMany
//    Set<Terms> groups;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Terms terms = (Terms) o;
        return Objects.equals(idTerm, terms.idTerm) && Objects.equals(dayName, terms.dayName) && Objects.equals(time, terms.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTerm, dayName, time);
    }
}
