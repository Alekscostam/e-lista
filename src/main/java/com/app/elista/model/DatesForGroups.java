package com.app.elista.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class DatesForGroups {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDatesForGroups;

    Long idDates;
    @ManyToOne
    @JoinColumn(name = "id_team")
    Teams teams;

    public DatesForGroups(Long idDates, Teams teams) {
        this.idDates = idDates;
        this.teams = teams;
    }


}
