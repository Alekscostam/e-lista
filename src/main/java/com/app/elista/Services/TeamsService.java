package com.app.elista.Services;

import com.app.elista.appcompany.AppCompany;
import com.app.elista.model.Teams;
import com.app.elista.model.extended.AllInfo;
import com.app.elista.model.extended.MoreInfo;
import com.app.elista.repositories.TeamsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TeamsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TeamsPricesService.class);;


    private final JdbcTemplate jdbcTemplate;
    TeamsRepository teamsRepository;

    @Autowired
    public TeamsService(JdbcTemplate jdbcTemplate, TeamsRepository teamsRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.teamsRepository = teamsRepository;
    }

    public Teams saveTeam(Teams teams) {
        return  teamsRepository.save(teams);
    }

    public List<Teams> findTeamIdsAndTeamNamesByUUID(UUID idCompany){

        String sqlTeams = "SELECT t.id_team, t.team_name  FROM teams t WHERE t.id_company='"+idCompany+"';";
        List<Teams> teams = jdbcTemplate.query(
                sqlTeams,
                new BeanPropertyRowMapper(Teams.class));

        return  teams;
    }

    public List<AllInfo> findAllInformationsByTeamUUID(UUID idCompany) {
        String sqlTeams = "SELECT t.id_team, t.team_name,t.terms,t.place, t.color, t.description, t.leader_name, t.start_date, t.end_date, t.first_free, t.free_space, t.group_size,t.id_company FROM teams t WHERE t.id_company='"+idCompany+"';";
        List<Teams> teams = jdbcTemplate.query(
                sqlTeams,
                new BeanPropertyRowMapper(Teams.class));

        List<MoreInfo> moreInfos = new ArrayList<>();
        List<AllInfo> allInfos = new ArrayList<>();
        List<String> terms;
        for (Teams team : teams) {

            String sql = "SELECT p.id_price, p.name,p.value, p.cycle, p.description, gp.id_group_price, gp.id_team FROM  groups_prices gp LEFT  JOIN  prices p on gp.id_price = p.id_price WHERE id_team='" + team.getIdTeam() + "';";

            List<String> query = jdbcTemplate.query(sql, (rs, rowNum) -> (
                    rs.getString(1) + ";" + rs.getString(2) + ";" + rs.getString(3) + ";" + rs.getString(4) + ";" + rs.getString(5) + ";" + rs.getString(6) + ";" + rs.getString(7)));

            for (String s : query) {
                String[] split = s.replaceAll("\\s+", "").split(";");
                MoreInfo moreInfo = new MoreInfo();
                moreInfo.setPriceId(split[0]);
                moreInfo.setPriceName(split[1]);
                moreInfo.setPriceValue(split[2]);
                moreInfo.setPriceCycle(split[3]);
                moreInfo.setPriceDescription(split[4]);
                moreInfo.setIdGroupPrice(split[5]);
                moreInfo.setTeamId(split[6]);
                moreInfos.add(moreInfo);
            }
            String[] splitTerms = team.getTerms().split(";");
            terms = Arrays.asList(splitTerms);
            allInfos.add(new AllInfo(team,terms,moreInfos));
        }

    return  allInfos;
    }


    public String deleteGroupById(Long id) {
        try {
            teamsRepository.deleteById(id);
            LOGGER.info("Usunieto grupe");
            return "Grupa została usunieta!";
        }catch (Exception ex){
            LOGGER.error(ex.getMessage());
            LOGGER.error("Najpierw nalezy usunąc użytkowników z danej grupy");
            return "Przed sunięciem grupy należy usunąc jej użytkowników!";
        }

    }

    public Teams findTeamById(String groupId) {
        return teamsRepository.findById(Long.valueOf(groupId)).get();

    }

    public List<Teams> findAllByCompany(AppCompany appCompany) {

        List<Teams> teams = teamsRepository.findAll().stream().filter(team -> team.getAppCompany().getIdCompany().equals(appCompany.getIdCompany())).collect(Collectors.toList());
        teams.forEach(t -> t.setAppCompany(null));
        return teams;


    }

    public List<String>  getTermsForTeams(Teams team) {
        String[] splitTerms = team.getTerms().split(";");
        List<String> terms = Arrays.asList(splitTerms);
        return terms;
    }
}
