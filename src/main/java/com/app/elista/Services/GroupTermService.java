package com.app.elista.Services;

import com.app.elista.model.GroupsTerms;
import com.app.elista.model.Teams;
import com.app.elista.model.Terms;
import com.app.elista.repositories.GroupsTermsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class GroupTermService {
    private final JdbcTemplate jdbcTemplate;
    final String INSERT_QUERY = "INSERT INTO groups_terms (id_team, id_term) values (?, ?)";
    GroupsTermsRepository groupsTermsRepository;

    @Autowired
    public GroupTermService(JdbcTemplate jdbcTemplate, GroupsTermsRepository groupsTermsRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.groupsTermsRepository = groupsTermsRepository;
    }

    public void insert(Teams team, List<Terms> terms) {
        List<GroupsTerms> groupsTermsList = new ArrayList<>();
        for (Terms term : terms) {

            GroupsTerms groupsTerms = new GroupsTerms(team, term);
            groupsTermsList.add(groupsTerms);
        }

        groupsTermsRepository.saveAll(groupsTermsList);

    }
}
