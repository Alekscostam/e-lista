package com.app.elista.model.extended;

import com.app.elista.Services.TeamService;
import com.app.elista.model.Teams;

import java.util.List;

public class AllInfo {
    Teams teams;
    List<MoreInfo> moreInfos;

    public AllInfo(Teams teams, List<MoreInfo> moreInfos) {
        this.teams = teams;
        this.moreInfos = moreInfos;
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
