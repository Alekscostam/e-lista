package com.app.elista.security.config;

import com.app.elista.appuser.AppCompanyService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final AppCompanyService appCompanyService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
@Autowired
    public WebSecurityConfig(AppCompanyService appCompanyService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.appCompanyService = appCompanyService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public WebSecurityConfig(boolean disableDefaults, AppCompanyService appCompanyService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        super(disableDefaults);
        this.appCompanyService = appCompanyService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/v*/registration/**")
                .permitAll()
                .anyRequest()
                .authenticated().and()
                .formLogin();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider();
        provider.setPasswordEncoder(bCryptPasswordEncoder);
        provider.setUserDetailsService(appCompanyService);
        return provider;
    }
}
