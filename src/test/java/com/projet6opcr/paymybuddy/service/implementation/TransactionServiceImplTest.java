package com.projet6opcr.paymybuddy.service.implementation;

import com.projet6opcr.paymybuddy.repository.TransactionRepository;
import com.projet6opcr.paymybuddy.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {

    @InjectMocks
    TransactionServiceImpl transactionService;

    @Mock
    TransactionRepository transactionRepository;



    @Test
    void getTransactionTest() {

    }

}