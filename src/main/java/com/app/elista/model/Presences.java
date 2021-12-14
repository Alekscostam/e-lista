package com.app.elista.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Presences {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Presences presences = (Presences) o;
        return Objects.equals(idPresence, presences.idPresence) && Objects.equals(idDates, presences.idDates) && Objects.equals(user, presences.user) && Objects.equals(presence, presences.presence);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPresence, idDates, user, presence);
    }

    @Id
    @SequenceGenerator(
            name = "presence_sequence",
            sequenceName = "presence_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "presence_sequence"
    )
    private Long idPresence;

    private Long idDates;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private Users user;
    private Boolean presence;


    public Presences(Boolean presence, Long idDates, Users user) {
        this.presence = presence;
        this.idDates = idDates;
        this.user = user;
    }

    public Boolean getPresence() {
        return presence;
    }

    public void setPresence(Boolean presence) {
        this.presence = presence;
    }

    public Presences() {

    }

    public Long getIdDates() {
        return idDates;
    }

    public void setIdDates(Long idDates) {
        this.idDates = idDates;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users users) {
        this.user = users;
    }

    public Long getIdPresence() {
        return idPresence;
    }

    public void setIdPresence(Long idPresence) {
        this.idPresence = idPresence;
    }
}
