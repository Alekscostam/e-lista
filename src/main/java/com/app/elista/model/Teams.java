package com.app.elista.model;

import com.app.elista.appcompany.AppCompany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class Teams {


    @SequenceGenerator(
            name = "Teams_sequence",
            sequenceName = "Teams_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "Teams_sequence"
    )
    private Long idTeam;
    private String teamName;
    @Column(length = 150)
    private String leaderName;
    @Column(length = 100)
    private String place;
    @Column(length = 10)
    private String startDate;
    @Column(length = 10)
    private String endDate;
    @Column(length = 3)
    private Short freeSpace;
    @Column(length = 4)
    private Short groupSize;
    private String color;
    private Boolean firstFree;
    @Column(length = 255)
    private String description;
    @Column(length = 355)
    private String terms;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_company", nullable=false)
    private AppCompany appCompany;

    public Teams(String teamName,
                 String leaderName,
                 String place,
                 String startDate,
                 String endDate,
                 Short freeSpace,
                 Short groupSize,
                 String color,
                 Boolean firstFree,
                 String description,
                 String  terms,
                 AppCompany appCompany) {
        this.teamName = teamName;
        this.leaderName = leaderName;
        this.place = place;
        this.startDate = startDate;
        this.endDate = endDate;
        this.freeSpace = freeSpace;
        this.groupSize = groupSize;
        this.color = color;
        this.firstFree = firstFree;
        this.description = description;
        this.terms = terms;
        this.appCompany = appCompany;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Teams teams = (Teams) o;
        return Objects.equals(idTeam, teams.idTeam) && Objects.equals(teamName, teams.teamName) && Objects.equals(leaderName, teams.leaderName) && Objects.equals(place, teams.place) && Objects.equals(startDate, teams.startDate) && Objects.equals(endDate, teams.endDate) && Objects.equals(freeSpace, teams.freeSpace) && Objects.equals(groupSize, teams.groupSize) && Objects.equals(color, teams.color) && Objects.equals(firstFree, teams.firstFree) && Objects.equals(description, teams.description) && Objects.equals(terms, teams.terms) && Objects.equals(appCompany, teams.appCompany);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTeam, teamName, leaderName, place, startDate, endDate, freeSpace, groupSize, color, firstFree, description, terms, appCompany);
    }

    @Override
    public String toString() {
        return "Teams{" +
                "idTeam=" + idTeam +
                ", teamName='" + teamName + '\'' +
                ", leaderName='" + leaderName + '\'' +
                ", place='" + place + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", freeSpace=" + freeSpace +
                ", groupSize=" + groupSize +
                ", color='" + color + '\'' +
                ", firstFree=" + firstFree +
                ", description='" + description + '\'' +
                ", terms='" + terms + '\'' +
                ", appCompany=" + appCompany +
                '}';
    }
}
