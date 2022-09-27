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
      return http.authorizeRequests()
//              .antMatchers("/" , "/loginAp").permitAll()
//              .antMatchers("/user").hasRole("USER")
//              .and()
//              .formLogin()
//              .loginProcessingUrl("/login_ap")       // link to submit username-password
//              .loginPage("/login_ap")
//              .usernameParameter("username")      // username field in login form
//              .passwordParameter("password")      // password field in login form
//              .defaultSuccessUrl("/")
//              .failureUrl("/login?error")
//              .and()
//              .logout()
//              .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//              .logoutSuccessUrl("/login")
//              .invalidateHttpSession(true)        // set invalidation state when logout
//              .deleteCookies("JSESSIONID")
//              .and()
//              .exceptionHandling()
//              .accessDeniedPage("/403")
//              .and().build();

              .antMatchers("/" , "/login_ap").permitAll()
              .antMatchers("/user").hasRole("USER")
              .anyRequest().authenticated()
              .and()
              .formLogin()
              .permitAll()
              .and()
              .oauth2Login()
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
