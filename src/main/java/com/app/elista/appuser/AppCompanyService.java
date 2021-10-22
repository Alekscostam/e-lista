package com.app.elista.appuser;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AppCompanyService implements UserDetailsService {

    private final static String COMPANY_NOT_FOUND_MSG="Firma z tym emailem %s nie istnieje";
    private final AppCompanyRepository appCompanyRepository;

    public AppCompanyService(AppCompanyRepository appCompanyRepository) {
        this.appCompanyRepository = appCompanyRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return appCompanyRepository.findByEmail(email).orElseThrow(()->new UsernameNotFoundException(String.format(COMPANY_NOT_FOUND_MSG,email)));
    }
}
