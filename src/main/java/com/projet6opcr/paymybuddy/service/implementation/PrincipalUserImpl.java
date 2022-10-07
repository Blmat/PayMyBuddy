package com.projet6opcr.paymybuddy.service.implementation;

import com.projet6opcr.paymybuddy.model.UserAccount;
import com.projet6opcr.paymybuddy.repository.UserRepository;
import com.projet6opcr.paymybuddy.service.PrincipalUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PrincipalUserImpl implements PrincipalUser {

    private final UserRepository userRepository;


    @Override
    public Optional<UserAccount> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userRepository.findByEmail(username);
    }

    @Override
    public UserAccount getCurrentUserOrThrowException() {
        return getCurrentUser()
                .orElseThrow(() -> new BadCredentialsException("User not connected"));
    }
}
