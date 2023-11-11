package org.choo.config;

import lombok.extern.log4j.Log4j;
import org.choo.security.CustomAccessDenieHandler;
import org.choo.security.CustomLoginSuccessHandler;
import org.choo.security.CustomNoOpPasswordEncoder;
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
                .antMatchers("/sample/all").permitAll()
                .antMatchers("/sample/admin").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/sample/member").access("hasRole('ROLE_MEMBER')");
        http.formLogin().successHandler(loginSuccessHandler());
        http.exceptionHandling().accessDeniedHandler(accessDeniedHandler());
        http.logout().invalidateHttpSession(true).deleteCookies("remember-me", "JSESSION_ID");
        http.rememberMe().key("choo").tokenRepository(persistentTokenRepository()).tokenValiditySeconds(604800);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        log.info("configure jdbc.................");
        //auth.inMemoryAuthentication().withUser("admin").password("{noop}admin").roles("ADMIN");
        //auth.inMemoryAuthentication().withUser("member").password("{noop}member").roles("MEMBER");
        //auth.inMemoryAuthentication().withUser("member").password("$2a$10$Uq40CNNuoak.G3aNHG6xuuFLrpuOVhzc7/9z2nmNVu31b0m8/bHVC").roles("MEMBER");
        //String queryUser = "select userid, userpw, enabled from c_member where userid = ?";
        //String queryDetails = "select userid, auth from c_member_auth where userid = ?";
        //auth.jdbcAuthentication().dataSource(dataSource).passwordEncoder(passwordEncoder()).usersByUsernameQuery(queryUser).authoritiesByUsernameQuery(queryDetails);
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
        //return new CustomNoOpPasswordEncoder();
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
