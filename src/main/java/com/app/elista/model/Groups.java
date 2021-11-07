package com.app.elista.model;

import com.app.elista.appcompany.AppCompany;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
public class Groups {
    @SequenceGenerator(
            name = "groups_sequence",
            sequenceName = "groups_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "groups_sequence"
    )
    private Long idGroup;
    @Column(length = 25)
    private String group_name;
    @Column(length = 50)
    private String leader_name;
    @Column(length = 100)
    private String place;
    @Column(length = 10)
    private String start_date;
    @Column(length = 10)
    private String end_date;
    @Column(length = 3)
    private Short free_space;
    @Column(length = 4)
    private Short group_size;
    @Column(length = 2)
    private Short payment_cycle;
    private String color;
    private Boolean first_free;
    @Column(length = 255)
    private String description;

    @ManyToOne
    @JoinColumn(name="id_company", nullable=false)
    private AppCompany appCompany;

    public Groups(String group_name, String leader_name, String place, String start_date, String end_date, Short free_space, Short group_size, Short payment_cycle, String color, Boolean first_free, String description, AppCompany appCompany) {
        this.group_name = group_name;
        this.leader_name = leader_name;
        this.place = place;
        this.start_date = start_date;
        this.end_date = end_date;
        this.free_space = free_space;
        this.group_size = group_size;
        this.payment_cycle = payment_cycle;
        this.color = color;
        this.first_free = first_free;
        this.description = description;
        this.appCompany = appCompany;
    }


    public Groups() {

    }


//    @ManyToMany
//    Set<Terms> terms;


    public Long getIdGroup() {
        return idGroup;
    }

    public void setIdGroup(Long idGroup) {
        this.idGroup = idGroup;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public String getLeader_name() {
        return leader_name;
    }

    public void setLeader_name(String leader_name) {
        this.leader_name = leader_name;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public Short getFree_space() {
        return free_space;
    }

    public void setFree_space(Short free_space) {
        this.free_space = free_space;
    }

    public Short getGroup_size() {
        return group_size;
    }

    public void setGroup_size(Short group_size) {
        this.group_size = group_size;
    }

    public short getPayment_cycle() {
        return payment_cycle;
    }

    public void setPayment_cycle(Short payment_cycle) {
        this.payment_cycle = payment_cycle;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Boolean getFirst_free() {
        return first_free;
    }

    public void setFirst_free(Boolean first_free) {
        this.first_free = first_free;
    }

    public String getDescription() {
        return description;
    }

    public AppCompany getAppCompany() {
        return appCompany;
    }

    public void setAppCompany(AppCompany appCompany) {
        this.appCompany = appCompany;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Groups groups = (Groups) o;
        return Objects.equals(idGroup, groups.idGroup) && Objects.equals(group_name, groups.group_name) && Objects.equals(leader_name, groups.leader_name) && Objects.equals(place, groups.place) && Objects.equals(start_date, groups.start_date) && Objects.equals(end_date, groups.end_date) && Objects.equals(free_space, groups.free_space) && Objects.equals(group_size, groups.group_size) && Objects.equals(payment_cycle, groups.payment_cycle) && Objects.equals(color, groups.color) && Objects.equals(first_free, groups.first_free) && Objects.equals(description, groups.description) && Objects.equals(appCompany, groups.appCompany);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idGroup, group_name, leader_name, place, start_date, end_date, free_space, group_size, payment_cycle, color, first_free, description, appCompany);
    }

}
