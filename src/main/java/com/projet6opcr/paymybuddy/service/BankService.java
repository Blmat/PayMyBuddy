package com.projet6opcr.paymybuddy.service;

import com.projet6opcr.paymybuddy.dto.BankAccountDTO;
import org.springframework.stereotype.Service;

@Service
public interface BankService {

    void addBank(BankAccountDTO bankAccountDTO);

    void transferMoney(Double amount);

}
