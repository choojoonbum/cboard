package org.choo.config;

import lombok.extern.log4j.Log4j;
import org.choo.security.CustomAccessDenieHandler;
import org.choo.security.CustomLoginSuccessHandler;
import org.choo.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@Log4j
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private DataSource dataSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')");
        http.formLogin().loginPage("/customLogin").loginProcessingUrl("/login");
        http.exceptionHandling().accessDeniedHandler(accessDeniedHandler());
        http.logout().logoutUrl("/customLogout").invalidateHttpSession(true).deleteCookies("remember-me", "JSESSION_ID");
        http.rememberMe().key("choo").tokenRepository(persistentTokenRepository()).tokenValiditySeconds(604800);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        log.info("configure jdbc.................");
        auth.userDetailsService(customUserService()).passwordEncoder(passwordEncoder());
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDenieHandler();
    }

    @Bean
    public AuthenticationSuccessHandler loginSuccessHandler() {
        return new CustomLoginSuccessHandler();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService customUserService() {
        return new CustomUserDetailsService();
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl repo = new JdbcTokenRepositoryImpl();
        repo.setDataSource(dataSource);
        return repo;
    }
}
