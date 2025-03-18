package com.trainer.workload.config.jwt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
@Configuration
public class BackendSecurityConfig {

    @Autowired
    private BackendJwtFilter backendJwtFilter;

    @Bean
    public SecurityFilterChain backendSecurityFilterChain(HttpSecurity http) throws Exception {
        log.info("[BackendSecurityConfig] Configuring Spring Security...");
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/workload/**").permitAll()
                        .anyRequest().authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(backendJwtFilter, UsernamePasswordAuthenticationFilter.class);
        log.info("[BackendSecurityConfig] Spring Security configuration completed.");
        return http.build();
    }
}
