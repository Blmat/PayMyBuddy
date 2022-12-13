package com.projet6opcr.paymybuddy.service;

import com.projet6opcr.paymybuddy.model.dto.TransactionDto;
import com.projet6opcr.paymybuddy.model.dto.TransactionInfoDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TransactionService {
    TransactionDto sendMoney(String friendEmail, TransactionDto transactionDTO);

}
