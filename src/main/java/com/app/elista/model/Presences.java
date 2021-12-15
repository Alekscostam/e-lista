package com.app.elista.model;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class Presences {



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

    public Presences(Long idDates, Users user) {
        this.idDates = idDates;
        this.user = user;
    }



}
