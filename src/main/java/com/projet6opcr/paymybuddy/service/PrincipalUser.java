package com.projet6opcr.paymybuddy.service;

import com.projet6opcr.paymybuddy.model.UserAccount;

import java.util.Optional;

public interface PrincipalUser {

    Optional<UserAccount> getCurrentUser();
    UserAccount getCurrentUserOrThrowException();

}
