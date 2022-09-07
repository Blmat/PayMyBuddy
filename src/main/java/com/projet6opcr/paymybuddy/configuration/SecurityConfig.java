package com.projet6opcr.paymybuddy.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collections;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
      return http.authorizeRequests()
              .antMatchers("/private").authenticated()
              .anyRequest().permitAll()
              .and()
              .formLogin()
              .and()
              .oauth2Login()
              .and().build();
    }

//    protected void configure (AuthenticationManagerBuilder auth)throws Exception {
//        auth.authenticationProvider(authenticationProvider());
//    }
    @Bean
    public UserDetailsService userDetailsService() {
        return new InMemoryUserDetailsManager(
                new User("user", "{noop}mdp", Collections.emptyList())
        );
    }
//    @Bean
//    public BCryptPasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }

}
