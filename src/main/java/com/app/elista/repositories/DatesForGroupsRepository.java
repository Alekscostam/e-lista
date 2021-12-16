package com.app.elista.repositories;

import com.app.elista.model.DatesForGroups;
import com.app.elista.model.Teams;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface DatesForGroupsRepository extends JpaRepository<DatesForGroups, Long> {

    @Query("SELECT dg.teams FROM DatesForGroups dg WHERE dg.idDates = :idDate")
    Optional<List<Teams>> findGroupsByDateId(Long idDate);
}
