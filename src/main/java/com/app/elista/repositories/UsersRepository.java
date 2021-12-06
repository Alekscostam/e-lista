package com.app.elista.repositories;

import com.app.elista.model.Teams;
import com.app.elista.model.Users;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Repository
@Transactional(readOnly = true)
public interface UsersRepository extends JpaRepository<Users, Long> {



}
