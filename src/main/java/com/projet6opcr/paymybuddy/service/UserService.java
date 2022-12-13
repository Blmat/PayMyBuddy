package com.projet6opcr.paymybuddy.service;

import com.projet6opcr.paymybuddy.model.dto.BankAccountDto;
import com.projet6opcr.paymybuddy.model.dto.UserDto;
import com.projet6opcr.paymybuddy.model.BankAccount;
import com.projet6opcr.paymybuddy.model.UserAccount;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserService {

    UserAccount getConnectedUser();

    Optional<UserAccount> findByEmail(String email);

    Optional<UserAccount> findUserById(Integer id);

    BankAccount addBankAccount(BankAccountDto bankAccount);

    void transferMoney(String friendEmail, Double amount);

    UserAccount addFriend(String friendEmail);

    Double addMoney(Double amount);

    void deleteUserById(Integer id);

    UserAccount saveUser(UserDto user);

}
