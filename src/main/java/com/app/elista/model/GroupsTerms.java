package com.app.elista.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class GroupsTerms {


    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "groups_terms_sequence"
    )
    @SequenceGenerator(
            name = "groups_terms_sequence",
            sequenceName = "groups_terms_sequence",
            allocationSize = 1
    )
    private Long idGroupTerm;

    @ManyToOne
    @JoinColumn(name = "id_team")
    Teams teams;

    @ManyToOne
    @JoinColumn(name = "id_term")
    Terms terms;

    public Long getId() {
        return idGroupTerm;
    }

    public void setId(Long idGroupTerm) {
        this.idGroupTerm = idGroupTerm;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupsTerms that = (GroupsTerms) o;
        return Objects.equals(idGroupTerm, that.idGroupTerm) && Objects.equals(teams, that.teams) && Objects.equals(terms, that.terms);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idGroupTerm, teams, terms);
    }

    public Long getIdGroupTerm() {
        return idGroupTerm;
    }

    public void setIdGroupTerm(Long idGroupTerm) {
        this.idGroupTerm = idGroupTerm;
    }

    public Teams getTeams() {
        return teams;
    }

    public void setTeams(Teams teams) {
        this.teams = teams;
    }

    public Terms getTerms() {
        return terms;
    }

    public void setTerms(Terms terms) {
        this.terms = terms;
    }

    public GroupsTerms(Teams teams, Terms terms) {
        this.teams = teams;
        this.terms = terms;
    }
}
