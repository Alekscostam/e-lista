package com.app.elista.model;

import com.app.elista.appcompany.AppCompany;
import javax.persistence.*;

@Entity
public class Users {


    @SequenceGenerator(
            name = "Users_sequence",
            sequenceName = "Users_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "Users_sequence"
    )

    @ManyToOne
    @JoinColumn(name="id_company", nullable=false)
    private AppCompany appCompany;

    private Long idUser;

   private String name;
   private String surname;
   private Integer phone;
   private String email;
   private Boolean adult;
   private String individualPriceName;
   private String individualPriceValue;
    private String individualPriceCycle;
    private String individualPriceDesc;
   private String currentPaymentDate;
   private String nextPaymentDate;
   private String dateOfRecording;

    public Users(AppCompany appCompany, String name, String surname, Integer phone, String email, Boolean adult, String currentPaymentDate, String nextPaymentDate, String dateOfRecording, Teams team, Prices price) {
        this.appCompany = appCompany;
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.email = email;
        this.adult = adult;
        this.currentPaymentDate = currentPaymentDate;
        this.nextPaymentDate = nextPaymentDate;
        this.dateOfRecording = dateOfRecording;
        this.teams = team;
        this.prices = price;

    }

    public String getIndividualPriceName() {
        return individualPriceName;
    }

    public void setIndividualPriceName(String individualPriceName) {
        this.individualPriceName = individualPriceName;
    }

    public String getIndividualPriceValue() {
        return individualPriceValue;
    }

    public void setIndividualPriceValue(String individualPriceValue) {
        this.individualPriceValue = individualPriceValue;
    }

    public String getIndividualPriceCycle() {
        return individualPriceCycle;
    }

    public void setIndividualPriceCycle(String individualPriceCycle) {
        this.individualPriceCycle = individualPriceCycle;
    }

    public String getIndividualPriceDesc() {
        return individualPriceDesc;
    }

    public void setIndividualPriceDesc(String individualPriceDesc) {
        this.individualPriceDesc = individualPriceDesc;
    }

    public String getCurrentPaymentDate() {
        return currentPaymentDate;
    }

    public void setCurrentPaymentDate(String currentPaymentDate) {
        this.currentPaymentDate = currentPaymentDate;
    }

    public String getNextPaymentDate() {
        return nextPaymentDate;
    }

    public void setNextPaymentDate(String nextPaymentDate) {
        this.nextPaymentDate = nextPaymentDate;
    }

    public String getDateOfRecording() {
        return dateOfRecording;
    }

    public void setDateOfRecording(String dateOfRecording) {
        this.dateOfRecording = dateOfRecording;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_team", nullable=false)
    private  Teams teams;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_price")
    private  Prices prices;

    public Users() {
    }


    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Integer getPhone() {
        return phone;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getAdult() {
        return adult;
    }

    public void setAdult(Boolean adult) {
        this.adult = adult;
    }

    public Teams getTeams() {
        return teams;
    }

    public void setTeams(Teams teams) {
        this.teams = teams;
    }

    public Prices getPrices() {
        return prices;
    }

    public void setPrices(Prices prices) {
        this.prices = prices;
    }
}

