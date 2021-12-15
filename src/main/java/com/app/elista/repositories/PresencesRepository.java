package com.app.elista.repositories;


import com.app.elista.model.Presences;
import com.app.elista.model.Prices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface PresencesRepository extends JpaRepository<Presences, Long> {

    @Query("delete from Presences p where p.user.idUser = ?1")
    void deletePresencesByUserId(Long id);
}
