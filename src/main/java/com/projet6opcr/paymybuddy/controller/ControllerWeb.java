//package com.projet6opcr.paymybuddy.controller;
//
//import org.springframework.security.core.Authentication;
//import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
//import org.springframework.security.oauth2.core.oidc.StandardClaimAccessor;
//import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.Optional;
//
//@RestController
//public class ControllerWeb {
//
//    @GetMapping("/*")
//    public String privatePage(Authentication authentication) {
//        return "Bienvenue ~["
//                + getName(authentication)
//                +" ]~ !";
//    }
//
//    private String getName(Authentication authentication) {
//        return Optional.of(authentication)
//                .filter(OAuth2AuthenticationToken.class::isInstance)
//                .map(OAuth2AuthenticationToken.class::cast)
//                .map(OAuth2AuthenticationToken::getPrincipal)
//                .map(DefaultOidcUser.class::cast)
//                .map(StandardClaimAccessor::getEmail)
//                .orElse(authentication.getName());
//    }
//}
