package com.app.elista.model;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

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
    @JoinColumn(name = "id_group")
    Groups groups;

    @ManyToOne
    @JoinColumn(name = "id_term")
    Terms terms;

    public Long getId() {
        return idGroupTerm;
    }

    public void setId(Long idGroupTerm) {
        this.idGroupTerm = idGroupTerm;
    }

    public GroupsTerms(Groups groups, Terms terms) {
        this.groups = groups;
        this.terms = terms;
    }

    public Long getIdGroupTerm() {
        return idGroupTerm;
    }

    public void setIdGroupTerm(Long idGroupTerm) {
        this.idGroupTerm = idGroupTerm;
    }

    public Groups getGroups() {
        return groups;
    }

    public void setGroups(Groups groups) {
        this.groups = groups;
    }

    public Terms getTerms() {
        return terms;
    }

    public void setTerms(Terms terms) {
        this.terms = terms;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupsTerms that = (GroupsTerms) o;
        return Objects.equals(idGroupTerm, that.idGroupTerm) && Objects.equals(groups, that.groups) && Objects.equals(terms, that.terms);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idGroupTerm, groups, terms);
    }
}
