package com.app.elista.Services;

import com.app.elista.model.Teams;
import com.app.elista.repositories.GroupsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class TeamService {

    private final JdbcTemplate jdbcTemplate;
    GroupsRepository groupsRepository;

    @Autowired
    public TeamService(JdbcTemplate jdbcTemplate, GroupsRepository groupsRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.groupsRepository = groupsRepository;
    }

    public Teams addGroup(Teams teams) {
        return  groupsRepository.save(teams);
    }

    public List<Teams> findAllByUUID(UUID idCompany) {

        String sql = "SELECT * FROM teams WHERE id_company='"+idCompany+"';";
        List<Teams> teams = jdbcTemplate.query(
                sql,
                new BeanPropertyRowMapper(Teams.class));


    return  teams;
    }
}
