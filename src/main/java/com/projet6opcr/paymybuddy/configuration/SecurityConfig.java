package com.projet6opcr.paymybuddy.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig{

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
      return http
//              .authorizeRequests()
//              .antMatchers(
//                      "/",
//                      "/js/**",
//                      "/css/**",
//                      "/img/**",
//                      "/webjars/**").permitAll()
//              .antMatchers("/user/**").hasRole("USER")
//              .anyRequest().authenticated()
//              .and()
//              .formLogin()
//              .loginPage("/login")
//              .permitAll()
//              .and()
//              .logout()
//              .invalidateHttpSession(true)
//              .clearAuthentication(true)
//              .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//              .logoutSuccessUrl("/login?logout")
//              .permitAll()
//              .and()
//              .exceptionHandling()
//              .accessDeniedPage("/error")
//              .and().build();
              .authorizeRequests()
              .antMatchers( "/",
                      "/js/**",
                      "/css/**",
                      "/img/**",
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

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web-> web.ignoring()
                .antMatchers("/registration**", "/css/**", "/img/**");
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
