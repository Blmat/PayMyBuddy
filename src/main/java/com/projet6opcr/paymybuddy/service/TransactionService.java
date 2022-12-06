package com.projet6opcr.paymybuddy.service;

import com.projet6opcr.paymybuddy.model.dto.TransactionDTO;
import com.projet6opcr.paymybuddy.model.dto.TransactionInfoDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TransactionService {
    TransactionDTO sendMoney(String friendEmail, TransactionDTO transactionDTO);

}
