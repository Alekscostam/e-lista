package com.app.elista.model;
import java.util.Objects;
import javax.persistence.*;
@Entity
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

    public GroupsPrices() {
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

    public GroupsPrices(Prices prices, Teams teams) {
        this.prices = prices;
        this.teams = teams;
    }

    public Long getIdGroupPrice() {
        return idGroupPrice;
    }

    public void setIdGroupPrice(Long idGroupPrice) {
        this.idGroupPrice = idGroupPrice;
    }

    public Prices getPrices() {
        return prices;
    }

    public void setPrices(Prices prices) {
        this.prices = prices;
    }

    public Teams getTeams() {
        return teams;
    }

    public void setTeams(Teams teams) {
        this.teams = teams;
    }
}
