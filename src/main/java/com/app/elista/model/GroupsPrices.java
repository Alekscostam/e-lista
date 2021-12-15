package com.app.elista.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;
import javax.persistence.*;
@Entity

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class GroupsPrices {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idGroupPrice;

    @ManyToOne
    @JoinColumn(name = "id_price")
    Prices prices;

    @ManyToOne
    @JoinColumn(name = "id_team")
    Teams teams;

    public GroupsPrices(Prices prices, Teams teams) {
        this.prices = prices;
        this.teams = teams;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupsPrices that = (GroupsPrices) o;
        return Objects.equals(idGroupPrice, that.idGroupPrice) && Objects.equals(prices, that.prices) && Objects.equals(teams, that.teams);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idGroupPrice, prices, teams);
    }

}
