package com.projet6opcr.paymybuddy.service;

import com.projet6opcr.paymybuddy.model.BankAccount;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface BankService {
    boolean checkIfBankAccountUserExists(Integer userId);

}
