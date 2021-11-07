package com.app.elista.model;

import java.util.Set;
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
    private String day_name;
    @Column(length = 5)
    private String time;

    public Terms() {
    }

    public Terms(String day_name, String time) {
        this.day_name = day_name;
        this.time = time;
    }


    public Long getIdTerm() {
        return idTerm;
    }

    public void setIdTerm(Long idTerm) {
        this.idTerm = idTerm;
    }

    public String getDay_name() {
        return day_name;
    }

    public void setDay_name(String day_name) {
        this.day_name = day_name;
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
        return Objects.equals(idTerm, terms.idTerm) && Objects.equals(day_name, terms.day_name) && Objects.equals(time, terms.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTerm, day_name, time);
    }
}
