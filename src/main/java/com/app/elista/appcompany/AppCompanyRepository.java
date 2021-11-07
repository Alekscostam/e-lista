package com.app.elista.appcompany;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional(readOnly = true)
public interface AppCompanyRepository extends JpaRepository<AppCompany, UUID> {

    Optional<AppCompany> findByEmail(String username);

    @Transactional
    @Modifying
    @Query("UPDATE AppCompany a " +
            "SET a.enabled = TRUE WHERE a.email = ?1")
    int enableAppCompany(String email);


}
