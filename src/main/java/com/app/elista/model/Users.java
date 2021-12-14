package com.app.elista.model;

import com.app.elista.appcompany.AppCompany;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Users {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Users users = (Users) o;
        return Objects.equals(idUser, users.idUser) && Objects.equals(appCompany, users.appCompany) && Objects.equals(name, users.name) && Objects.equals(surname, users.surname) && Objects.equals(phone, users.phone) && Objects.equals(email, users.email) && Objects.equals(adult, users.adult) && Objects.equals(individualPriceId, users.individualPriceId) && Objects.equals(individualPriceName, users.individualPriceName) && Objects.equals(individualPriceValue, users.individualPriceValue) && Objects.equals(individualPriceCycle, users.individualPriceCycle) && Objects.equals(individualPriceDesc, users.individualPriceDesc) && Objects.equals(currentPaymentDate, users.currentPaymentDate) && Objects.equals(nextPaymentDate, users.nextPaymentDate) && Objects.equals(dateOfRecording, users.dateOfRecording) && Objects.equals(teams, users.teams);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUser, appCompany, name, surname, phone, email, adult, individualPriceId, individualPriceName, individualPriceValue, individualPriceCycle, individualPriceDesc, currentPaymentDate, nextPaymentDate, dateOfRecording, teams);
    }

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
   private Long individualPriceId;
    private String individualPriceName;
    private Integer individualPriceValue;
    private Short individualPriceCycle;
    private String individualPriceDesc;
    private String currentPaymentDate;
    private String nextPaymentDate;

    public Long getIndividualPriceId() {
        return individualPriceId;
    }

    public void setIndividualPriceId(Long individualPriceId) {
        this.individualPriceId = individualPriceId;
    }

    private String dateOfRecording;

    public Users(AppCompany appCompany, String name, String surname, Integer phone, String email, Boolean adult, Long individualPriceId, String individualPriceName, Integer individualPriceValue, Short individualPriceCycle, String individualPriceDesc, String currentPaymentDate, String nextPaymentDate, String dateOfRecording, Teams teams) {
        this.appCompany = appCompany;
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.email = email;
        this.adult = adult;
        this.individualPriceId = individualPriceId;
        this.individualPriceName = individualPriceName;
        this.individualPriceValue = individualPriceValue;
        this.individualPriceCycle = individualPriceCycle;
        this.individualPriceDesc = individualPriceDesc;
        this.currentPaymentDate = currentPaymentDate;
        this.nextPaymentDate = nextPaymentDate;
        this.dateOfRecording = dateOfRecording;
        this.teams = teams;
    }





    public Users(AppCompany appCompany, String name, String surname, Integer phone, String email, Boolean adult, String currentPaymentDate, String nextPaymentDate, String dateOfRecording, Prices price) {
        this.appCompany = appCompany;
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.email = email;
        this.adult = adult;
        this.currentPaymentDate = currentPaymentDate;
        this.nextPaymentDate = nextPaymentDate;
        this.dateOfRecording = dateOfRecording;


    }


    public String getIndividualPriceName() {
        return individualPriceName;
    }

    public void setIndividualPriceName(String individualPriceName) {
        this.individualPriceName = individualPriceName;
    }

    public Integer getIndividualPriceValue() {
        return individualPriceValue;
    }

    public void setIndividualPriceValue(Integer individualPriceValue) {
        this.individualPriceValue = individualPriceValue;
    }

    public Short getIndividualPriceCycle() {
        return individualPriceCycle;
    }

    public void setIndividualPriceCycle(Short individualPriceCycle) {
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

    @ManyToOne(optional = true )
    @JoinColumn(name="id_team", nullable=false)
    private  Teams teams;



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



}

