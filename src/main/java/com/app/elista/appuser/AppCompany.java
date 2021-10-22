package com.app.elista.appuser;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
public class AppCompany implements UserDetails {
    @Id
    @Column(name = "id_firma", nullable = false)
    @SequenceGenerator(
            name = "firma_sequence",
            sequenceName = "firma_sequence",
            allocationSize = 1)
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "firma_sequence"
    )
    private Long idFirma;

    @Column(length = 35)
    private String nazwa;
    @Column(length = 25)
    private String email;



    @Column(length = 25)
    private String haslo;
    @Column(length = 10)
    private String dataUtworzenia;
    @Enumerated(EnumType.STRING)
    private AppCompanyRole appCompanyRole;
    private Boolean locked;
    private Boolean enabled;

    public AppCompany(String name,
                      String email,
                      String haslo,
                      AppCompanyRole appCompanyRole,
                      Boolean locked,
                      Boolean enabled) {
        this.nazwa = name;
        this.email = email;
        this.haslo = haslo;
        this.appCompanyRole = appCompanyRole;
        this.locked = locked;
        this.enabled = enabled;
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
        return haslo;
    }

    @Override
    public String getUsername() {
        return nazwa;
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
}
