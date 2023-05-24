package com.cinqict.workshop.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.LdapShaPasswordEncoder;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
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
               //.formLogin(Customizer.withDefaults())
               .httpBasic(Customizer.withDefaults())
               .build();
    }

//    @Bean
//    PasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
//    }

    @Bean
    PasswordEncoder passwordEncoder(){
        //default encoding is Bcrypt
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService(PasswordEncoder passwordEncoder) {

        UserDetails user = User.builder()
                .username("spring")
                .password(passwordEncoder().encode("password"))
                .roles("ADMIN")
                .build();

        UserDetails user1 = User.builder()
                .username("user")
                .password("{ldap}"+new LdapShaPasswordEncoder().encode("password"))
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(user, user1);
    }
}

