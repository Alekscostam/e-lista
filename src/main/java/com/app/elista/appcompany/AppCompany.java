package com.app.elista.appcompany;

import com.app.elista.model.Groups;
import com.app.elista.model.Offer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;


@Entity
public class AppCompany implements UserDetails {


    @Id
    @Column(name = "id_company", nullable = false)
    @SequenceGenerator(
            name = "company_sequence_UUID",
            sequenceName = "company_sequence",
            allocationSize = 1)
    @GeneratedValue(generator = "UUID"
    )
    private UUID idCompany;
    @Column(length = 35)
    private String name;
    @Column(length = 25)
    private String email;
    private String password;
    @Column(length = 45)
    private String address;
    @Column(length = 9)
    private String phone;
    @Column(length = 10)
    private String creationDate;
    @Enumerated(EnumType.STRING)
    private Offer offer;
    @Enumerated(EnumType.STRING)
    private AppCompanyRole appCompanyRole;
    private Boolean locked = false;
    private Boolean enabled = true;
    @OneToMany(mappedBy="appCompany")
    private Set<Groups> groups;

    public AppCompany(String name,
                      String email,
                      String password,
                      String address,
                      String phone,
                      Offer offer,
                      String creationDate,
                      AppCompanyRole appCompanyRole
    ) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.address = address;
        this.phone = phone;
        this.offer = offer;
        this.creationDate = creationDate;
        this.appCompanyRole = appCompanyRole;
    }


    public UUID getIdCompany() {
        return idCompany;
    }

    public void setIdCompany(UUID idFirma) {
        this.idCompany = idFirma;
    }

    public String getName() {
        return name;
    }

    public void setName(String nazwa) {
        this.name = nazwa;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String dataUtworzenia) {
        this.creationDate = dataUtworzenia;
    }

    public AppCompanyRole getAppCompanyRole() {
        return appCompanyRole;
    }

    public void setAppCompanyRole(AppCompanyRole appCompanyRole) {
        this.appCompanyRole = appCompanyRole;
    }

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppCompany that = (AppCompany) o;
        return Objects.equals(idCompany, that.idCompany) && Objects.equals(name, that.name) && Objects.equals(email, that.email) && Objects.equals(password, that.password) && Objects.equals(address, that.address) && Objects.equals(phone, that.phone) && Objects.equals(creationDate, that.creationDate) && appCompanyRole == that.appCompanyRole && Objects.equals(locked, that.locked) && Objects.equals(enabled, that.enabled);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCompany, name, email, password, address, phone, creationDate, appCompanyRole, locked, enabled);
    }

    public AppCompany() {

    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(appCompanyRole.name());
        return Collections.singleton(authority);
    }
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
