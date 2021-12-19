package com.app.elista.model.extended;

import com.app.elista.model.Prices;
import com.app.elista.model.Teams;

import java.util.List;
import java.util.Objects;

public class AllInfo {
    Teams teams;
    List<String> terms;
    List<Prices> allPrices;
    List<Prices> checkedPrices;

    public List<Prices> getCheckedPrices() {
        return checkedPrices;
    }

    public void setCheckedPrices(List<Prices> checkedPrices) {
        this.checkedPrices = checkedPrices;
    }

    public AllInfo(Teams teams, List<String> terms, List<Prices> allPrices, List<Prices> checkedPrices) {
        this.teams = teams;
        this.terms = terms;
        this.allPrices = allPrices;
        this.checkedPrices = checkedPrices;
    }

    public AllInfo(Teams teams, List<String> terms, List<Prices> allPrices) {
        this.teams = teams;
        this.terms = terms;
        this.allPrices = allPrices;
    }

    public List<String> getTerms() {
        return terms;
    }

    public void setTerms(List<String> terms) {
        this.terms = terms;
    }

    public Teams getTeams() {
        return teams;
    }

    public void setTeams(Teams teams) {
        this.teams = teams;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AllInfo allInfo = (AllInfo) o;
        return Objects.equals(teams, allInfo.teams) && Objects.equals(terms, allInfo.terms) && Objects.equals(allPrices, allInfo.allPrices) && Objects.equals(checkedPrices, allInfo.checkedPrices);
    }

    @Override
    public int hashCode() {
        return Objects.hash(teams, terms, allPrices, checkedPrices);
    }

    public List<Prices> getAllPrices() {
        return allPrices;
    }

    public void setAllPrices(List<Prices> allPrices) {
        this.allPrices = allPrices;
    }
}
