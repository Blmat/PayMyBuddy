package com.projet6opcr.paymybuddy.service;

import com.projet6opcr.paymybuddy.dto.BankAccountDTO;
import com.projet6opcr.paymybuddy.model.BankAccount;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface BankService {

    void addBank(BankAccount bank);

    void addMoney(Double amount);

    void transferMoney(Double amount);

    Optional<BankAccount> getBank() throws Exception;

    boolean checkIfBankAccountUserExists(Integer userId);

}
