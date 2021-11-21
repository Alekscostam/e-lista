package com.app.elista.repositories;


import com.app.elista.model.Teams;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Repository
@Transactional(readOnly = true)
public interface GroupsRepository  extends JpaRepository<Teams, Long> {

//    User findByUUID(UUID id);
}
