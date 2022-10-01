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

    BankAccount addBankAccount(Integer userInt, BankAccount bankAccount);

    void transferMoney(String friendEmail, Double amount);

    UserAccount addFriend(String friendEmail);

    @NotBlank
    Double addMoney(String userMail, Double amount);

    void deleteUserById(Integer id);

    void saveUser(UserAccount userAccount);
}
