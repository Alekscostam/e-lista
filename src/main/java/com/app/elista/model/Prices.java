package com.app.elista.model;




import com.app.elista.appcompany.AppCompany;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Prices {


    public Prices() {
    }

    @SequenceGenerator(
            name = "Prices_sequence",
            sequenceName = "Prices_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "Prices_sequence"
    )
    private Long idPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_company", nullable=false)
    private AppCompany appCompany;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Prices prices = (Prices) o;
        return Objects.equals(idPrice, prices.idPrice) && Objects.equals(name, prices.name) && Objects.equals(value, prices.value) && Objects.equals(cycle, prices.cycle)  && Objects.equals(description, prices.description);
    }

    public Long getIdPrice() {
        return idPrice;
    }

    public void setIdPrice(Long idPrice) {
        this.idPrice = idPrice;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPrice, name, value, cycle, description);
    }

    private String name;
    private Integer value;
    private Short cycle;

    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Short getCycle() {
        return cycle;
    }

    public void setCycle(Short cycle) {
        this.cycle = cycle;
    }



    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Prices(AppCompany appCompany, String name, Integer value, Short cycle, String description) {
        this.appCompany = appCompany;
        this.name = name;
        this.value = value;
        this.cycle = cycle;
        this.description = description;
    }

    public AppCompany getAppCompany() {
        return appCompany;
    }

    public void setAppCompany(AppCompany appCompany) {
        this.appCompany = appCompany;
    }
}
