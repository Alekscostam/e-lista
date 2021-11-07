package com.app.elista.model.repositories;


import com.app.elista.model.Groups;
import com.app.elista.model.Terms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

import java.util.UUID;

@Repository
@Transactional(readOnly = true)
public interface GroupsRepository  extends JpaRepository<Groups, Long> {
}
