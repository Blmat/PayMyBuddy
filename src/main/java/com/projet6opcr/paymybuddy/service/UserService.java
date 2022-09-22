package com.projet6opcr.paymybuddy.service;

import com.projet6opcr.paymybuddy.model.BankAccount;
import com.projet6opcr.paymybuddy.model.UserAccount;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotBlank;
import java.util.Optional;
import java.util.Set;

@Service
public interface UserService {

    Optional<UserAccount> findByEmail(String email);

    void saveUser(UserAccount userAccount);

    Optional<UserAccount> findUserById(Integer id);

    void deleteUserById(Integer id);

    boolean existsByEmail(String email);

}
