package com.projet6opcr.paymybuddy.service;

import com.projet6opcr.paymybuddy.dto.BankAccountDTO;
import com.projet6opcr.paymybuddy.model.Bank;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface BankService {

    void addBank(BankAccountDTO bankAccountDTO);

    void addMoney(Double amount);

    void transferMoney(Double amount);

    Optional<Bank> getBank() throws Exception;

}
