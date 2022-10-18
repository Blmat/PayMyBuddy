package com.projet6opcr.paymybuddy.service;

import com.projet6opcr.paymybuddy.dto.UserDTO;
import com.projet6opcr.paymybuddy.model.BankAccount;
import com.projet6opcr.paymybuddy.model.UserAccount;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotBlank;
import java.util.Optional;

@Service
public interface UserService {

    UserAccount getConnectedUser();

    Optional<UserAccount> findByEmail(String email);

    Optional<UserAccount> findUserById(Integer id);

    BankAccount addBankAccount(Integer userInt, BankAccount bankAccount);

    void transferMoney(String friendEmail, Double amount);

    UserAccount addFriend(String friendEmail);

    @NotBlank
    Double addMoney(String userMail, Double amount);

    void deleteUserById(Integer id);

    UserAccount saveUser(UserDTO user);
}
