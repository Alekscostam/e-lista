package com.app.elista.model;

import com.app.elista.appcompany.AppCompany;
import javax.persistence.*;
import java.util.Objects;

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
    private Long idUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_company", nullable=false)
    private AppCompany appCompany;

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

    @Override
    public String toString() {
        return "Users{" +
                "idUser=" + idUser +
                ", appCompany=" + appCompany +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", phone=" + phone +
                ", email='" + email + '\'' +
                ", adult=" + adult +
                ", individualPriceName='" + individualPriceName + '\'' +
                ", individualPriceValue='" + individualPriceValue + '\'' +
                ", individualPriceCycle='" + individualPriceCycle + '\'' +
                ", individualPriceDesc='" + individualPriceDesc + '\'' +
                ", currentPaymentDate='" + currentPaymentDate + '\'' +
                ", nextPaymentDate='" + nextPaymentDate + '\'' +
                ", dateOfRecording='" + dateOfRecording + '\'' +
                ", teams=" + teams +
                ", prices=" + prices +
                '}';
    }

    public AppCompany getAppCompany() {
        return appCompany;
    }

    public void setAppCompany(AppCompany appCompany) {
        this.appCompany = appCompany;
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

    @ManyToOne
    @JoinColumn(name="id_team", nullable=false)
    private  Teams teams;

    @ManyToOne
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
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Users users = (Users) o;
        return Objects.equals(idUser, users.idUser) && Objects.equals(appCompany, users.appCompany) && Objects.equals(name, users.name) && Objects.equals(surname, users.surname) && Objects.equals(phone, users.phone) && Objects.equals(email, users.email) && Objects.equals(adult, users.adult) && Objects.equals(individualPriceName, users.individualPriceName) && Objects.equals(individualPriceValue, users.individualPriceValue) && Objects.equals(individualPriceCycle, users.individualPriceCycle) && Objects.equals(individualPriceDesc, users.individualPriceDesc) && Objects.equals(currentPaymentDate, users.currentPaymentDate) && Objects.equals(nextPaymentDate, users.nextPaymentDate) && Objects.equals(dateOfRecording, users.dateOfRecording) && Objects.equals(teams, users.teams) && Objects.equals(prices, users.prices);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUser, appCompany, name, surname, phone, email, adult, individualPriceName, individualPriceValue, individualPriceCycle, individualPriceDesc, currentPaymentDate, nextPaymentDate, dateOfRecording, teams, prices);
    }
}

