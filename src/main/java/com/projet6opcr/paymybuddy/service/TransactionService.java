package com.projet6opcr.paymybuddy.service;

import com.projet6opcr.paymybuddy.dto.TransactionDTO;
import com.projet6opcr.paymybuddy.model.Transaction;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TransactionService {
//    void sendMoney(TransactionDTO transactionDTO);
    void sendMoney(String friendEmail,TransactionDTO transactionDTO);

}
