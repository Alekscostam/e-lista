package com.app.elista.Services;

import com.app.elista.appcompany.AppCompany;
import com.app.elista.model.Prices;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(TeamsService.class);;


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

    public List<AllInfo> findAllInfoByAppCompanyUUID(UUID idCompany) {
        String sqlTeams = "SELECT t.id_team, t.team_name,t.terms,t.place, t.color, t.description, t.leader_name, t.start_date, t.end_date, t.first_free, t.free_space, t.group_size,t.id_company FROM teams t WHERE t.id_company='"+idCompany+"';";
        List<Teams> teams = jdbcTemplate.query(
                sqlTeams,
                new BeanPropertyRowMapper(Teams.class));

        List<Prices> prices = new ArrayList<>();
        List<AllInfo> allInfos = new ArrayList<>();

        List<String> terms = new ArrayList<>();

        for (Teams team : teams) {
            String[] splitTerms = team.getTerms().split(";");
            terms = Arrays.asList(splitTerms);
            allInfos.add(new AllInfo(team, terms, prices));
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
            return "Przed usnięciem grupy należy usunąć jej użytkowników!";
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

    public List<Teams> findAllByCompanyWithoutAppCompanyReset(AppCompany appCompany) {

        List<Teams> teams = teamsRepository.findAll().stream().filter(team -> team.getAppCompany().getIdCompany().equals(appCompany.getIdCompany())).collect(Collectors.toList());

        return teams;

    }

    public List<String>  getTermsForTeams(Teams team) {
        String[] splitTerms = team.getTerms().split(";");
        List<String> terms = Arrays.asList(splitTerms);
        return terms;
    }
}
