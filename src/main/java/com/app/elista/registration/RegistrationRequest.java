package com.app.elista.registration;

import com.app.elista.model.Offer;

import java.util.Objects;


public class RegistrationRequest {

    public RegistrationRequest(String name, String email, String password, String address, String phone, Offer offer) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.address = address;
        this.phone = phone;
        this.offer = offer;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    private final String name;
    private final String email;
    private final String password;
    private final String address;
    private final String phone;
    private final Offer offer;
    private String creationDate;


    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegistrationRequest that = (RegistrationRequest) o;
        return Objects.equals(name, that.name) && Objects.equals(email, that.email) && Objects.equals(password, that.password) && Objects.equals(address, that.address) && Objects.equals(phone, that.phone) && offer == that.offer && Objects.equals(creationDate, that.creationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, email, password, address, phone, offer, creationDate);
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public Offer getOffer() {
        return offer;
    }
}
