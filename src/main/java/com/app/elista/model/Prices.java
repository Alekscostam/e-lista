package com.app.elista.model;




import com.app.elista.appcompany.AppCompany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class Prices {

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

    private String name;
    private Integer value;
    private Short cycle;
    private String description;

    public Prices(AppCompany appCompany, String name, Integer value, Short cycle, String description) {
        this.appCompany = appCompany;
        this.name = name;
        this.value = value;
        this.cycle = cycle;
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Prices prices = (Prices) o;
        return Objects.equals(idPrice, prices.idPrice) && Objects.equals(name, prices.name) && Objects.equals(value, prices.value) && Objects.equals(cycle, prices.cycle)  && Objects.equals(description, prices.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPrice, name, value, cycle, description);
    }

}
