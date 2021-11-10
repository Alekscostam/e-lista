package com.app.elista.model;

import com.app.elista.appcompany.AppCompany;

import javax.persistence.*;

@Entity
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
    @Column(length = 25)
    private String teamName;
    @Column(length = 50)
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
    @Column(length = 2)
    private Short paymentCycle;
    private String color;
    private Boolean firstFree;
    @Column(length = 255)
    private String description;

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
                 Short paymentCycle,
                 String color,
                 Boolean firstFree,
                 String description,
                 AppCompany appCompany) {
        this.teamName = teamName;
        this.leaderName = leaderName;
        this.place = place;
        this.startDate = startDate;
        this.endDate = endDate;
        this.freeSpace = freeSpace;
        this.groupSize = groupSize;
        this.paymentCycle = paymentCycle;
        this.color = color;
        this.firstFree = firstFree;
        this.description = description;
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

    public short getPaymentCycle() {
        return paymentCycle;
    }

    public void setPaymentCycle(Short paymentCycle) {
        this.paymentCycle = paymentCycle;
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
