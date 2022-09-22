package com.projet6opcr.paymybuddy.service;

import com.projet6opcr.paymybuddy.model.BankAccount;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface BankService {
    Optional<BankAccount> getBank() throws Exception;
    boolean checkIfBankAccountUserExists(Integer userId);

}
