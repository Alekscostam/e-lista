package com.app.elista.appuser;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface AppCompanyRepository {
    Optional<UserDetails> findByEmail(String username);
}
