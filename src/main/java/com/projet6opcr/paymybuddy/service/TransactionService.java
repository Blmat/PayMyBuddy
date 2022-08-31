package com.projet6opcr.paymybuddy.service;

import com.projet6opcr.paymybuddy.dto.TransactionDTO;
import org.springframework.stereotype.Service;

@Service
public interface TransactionService {

    void sendMoney(TransactionDTO transactionDTO);

}
