package com.app.elista.model.extended;

import com.app.elista.model.Teams;

import java.util.List;

public class AllInfo {
    Teams teams;
    List<String> terms;
    List<MoreInfo> moreInfos;

    public AllInfo(Teams teams, List<String> terms, List<MoreInfo> moreInfos) {
        this.teams = teams;
        this.terms = terms;
        this.moreInfos = moreInfos;
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

    public List<MoreInfo> getMoreInfos() {
        return moreInfos;
    }

    public void setMoreInfos(List<MoreInfo> moreInfos) {
        this.moreInfos = moreInfos;
    }
}