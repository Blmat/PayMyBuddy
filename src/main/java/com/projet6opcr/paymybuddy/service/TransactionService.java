package com.projet6opcr.paymybuddy.service;

import com.projet6opcr.paymybuddy.dto.TransactionDTO;
import com.projet6opcr.paymybuddy.model.Transaction;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TransactionService {
    void sendMoney(String friendEMail,TransactionDTO transactionDTO);

}
