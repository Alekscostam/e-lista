package com.app.elista.model;

import com.app.elista.appcompany.AppCompany;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class Teams {
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

    public String getTerms() {
        return terms;
    }

    public void setTerms(String terms) {
        this.terms = terms;
    }

    @ManyToOne
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


    public Teams() {

    }

//    @ManyToMany
//    Set<Terms> terms;

    public Long getIdTeam() {
        return idTeam;
    }

    public void setIdTeam(Long idTeam) {
        this.idTeam = idTeam;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getLeaderName() {
        return leaderName;
    }

    public void setLeaderName(String leaderName) {
        this.leaderName = leaderName;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Short getFreeSpace() {
        return freeSpace;
    }

    public void setFreeSpace(Short freeSpace) {
        this.freeSpace = freeSpace;
    }

    public Short getGroupSize() {
        return groupSize;
    }

    public void setGroupSize(Short groupSize) {
        this.groupSize = groupSize;
    }


    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Boolean getFirstFree() {
        return firstFree;
    }

    public void setFirstFree(Boolean firstFree) {
        this.firstFree = firstFree;
    }

    public String getDescription() {
        return description;
    }

    public AppCompany getAppCompany() {
        return appCompany;
    }

    public void setAppCompany(AppCompany appCompany) {
        this.appCompany = appCompany;
    }

    public void setDescription(String description) {
        this.description = description;
    }




}
