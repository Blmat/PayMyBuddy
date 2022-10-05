package com.projet6opcr.paymybuddy.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig{

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
      return http
              .authorizeRequests()
              .antMatchers( "/",
                      "/js/**",
                      "/css/**",
                      "/img/**",
                      "/registration**",
                      "/webjars/**").permitAll()
              .antMatchers("/user/**").hasRole("USER")
              .anyRequest().authenticated()
              .and()
              .formLogin()
              .loginProcessingUrl("/login")
              .loginPage("/login")
              .permitAll()
              .usernameParameter("username")
              .passwordParameter("password")
              .and()
              .oauth2Login()
              .loginPage("/registration")
              .defaultSuccessUrl("/home?success")
              .and()
              .logout()
              .invalidateHttpSession(true)
              .clearAuthentication(true)
              .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
              .logoutSuccessUrl("/index")
              .permitAll()
              .and()
              .exceptionHandling()
              .accessDeniedPage("/error")
              .and().build();
    }
}
