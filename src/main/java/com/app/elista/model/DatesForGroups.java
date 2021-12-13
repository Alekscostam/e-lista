package com.app.elista.model;

import javax.persistence.*;

@Entity
public class DatesForGroups {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDatesForGroups;

    public DatesForGroups() {
    }

    Long idDates;

    @ManyToOne
    @JoinColumn(name = "id_team")
    Teams teams;

    public Long getIdDatesForGroups() {
        return idDatesForGroups;
    }

    public void setIdDatesForGroups(Long idDatesForGroups) {
        this.idDatesForGroups = idDatesForGroups;
    }

    public Long getIdDates() {
        return idDates;
    }

    public void setIdDates(Long idDates) {
        this.idDates = idDates;
    }

    public Teams getTeams() {
        return teams;
    }

    public void setTeams(Teams teams) {
        this.teams = teams;
    }

    public DatesForGroups(Long idDates, Teams teams) {
        this.idDates = idDates;
        this.teams = teams;
    }

    public Long getId() {
        return idDatesForGroups;
    }

    public void setId(Long id) {
        this.idDatesForGroups = id;
    }
}
