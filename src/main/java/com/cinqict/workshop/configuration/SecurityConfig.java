package com.cinqict.workshop.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
       return http
               .csrf().disable()
               .authorizeHttpRequests(authorize -> {
                   authorize
                           .requestMatchers(HttpMethod.POST).authenticated()
                           .requestMatchers(HttpMethod.DELETE).authenticated()
                           .requestMatchers(HttpMethod.PUT).authenticated()
                           .requestMatchers(HttpMethod.GET).permitAll()
                          // .requestMatchers("/error/**").permitAll()
                           .anyRequest().denyAll();
               })
               .formLogin(Customizer.withDefaults())
               .httpBasic(Customizer.withDefaults())
               .build();
    }
}

