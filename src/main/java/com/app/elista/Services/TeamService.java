package com.app.elista.Services;

import com.app.elista.model.Teams;
import com.app.elista.repositories.GroupsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamService {

    GroupsRepository groupsRepository;

    @Autowired
    public TeamService(GroupsRepository groupsRepository) {
        this.groupsRepository = groupsRepository;
    }

    public void addGroup(Teams teams) {
        groupsRepository.save(teams);

    }
}
