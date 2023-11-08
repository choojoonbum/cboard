package org.choo.config;

import lombok.extern.log4j.Log4j;
import org.choo.security.CustomAccessDenieHandler;
import org.choo.security.CustomLoginSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
@Log4j
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/sample/all").permitAll()
                .antMatchers("/sample/admin").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/sample/member").access("hasRole('ROLE_MEMBER')");
        http.formLogin().successHandler(loginSuccessHandler());
        http.exceptionHandling().accessDeniedHandler(accessDeniedHandler());
        http.logout();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        log.info("configure.................");
        auth.inMemoryAuthentication().withUser("admin").password("{noop}admin").roles("ADMIN");
        //auth.inMemoryAuthentication().withUser("member").password("{noop}member").roles("MEMBER");
        auth.inMemoryAuthentication().withUser("member").password("$2a$10$Uq40CNNuoak.G3aNHG6xuuFLrpuOVhzc7/9z2nmNVu31b0m8/bHVC").roles("MEMBER");
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
}
