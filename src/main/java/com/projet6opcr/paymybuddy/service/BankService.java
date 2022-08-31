package com.projet6opcr.paymybuddy.service;

import com.projet6opcr.paymybuddy.dto.BankAccountDTO;
import com.projet6opcr.paymybuddy.model.Bank;
import org.springframework.stereotype.Service;

@Service
public interface BankService {

    void addBank(BankAccountDTO bankAccountDTO);

    void addBalance(Double amount);

    void transferMoney(Double amount);

    Bank getBank();
}
