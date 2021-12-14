package com.app.elista.model.extended;

import com.app.elista.model.Prices;
import com.app.elista.model.Teams;

import java.util.List;

public class TermsPricesTeams {
    List<String> terms;
    List<Prices> prices;
    Teams team;

    public TermsPricesTeams(Teams team,List<String> terms) {
        this.terms = terms;
        this.team = team;
    }

    public TermsPricesTeams(List<Prices> prices, Teams team) {
        this.prices = prices;
        this.team = team;
    }

    public TermsPricesTeams(List<String> terms, List<Prices> prices) {
        this.terms = terms;
        this.prices = prices;
    }
    public TermsPricesTeams(List<String> terms, List<Prices> prices, Teams team) {
        this.terms = terms;
        this.prices = prices;
        this.team = team;
    }

    public List<String> getTerms() {
        return terms;
    }

    public void setTerms(List<String> terms) {
        this.terms = terms;
    }

    public List<Prices> getPrices() {
        return prices;
    }

    public void setPrices(List<Prices> prices) {
        this.prices = prices;
    }

    public Teams getTeam() {
        return team;
    }

    public void setTeams(Teams team) {
        this.team = team;
    }


}
