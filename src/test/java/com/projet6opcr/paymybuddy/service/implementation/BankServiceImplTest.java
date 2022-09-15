package com.projet6opcr.paymybuddy.service.implementation;

import com.projet6opcr.paymybuddy.model.BankAccount;
import com.projet6opcr.paymybuddy.repository.BankRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BankServiceImplTest {

    @InjectMocks
    BankServiceImpl bankService;
    @Mock
    BankRepository bankRepository;

    static BankAccount bank;


    @BeforeAll
   static void setUp() throws Exception {

        bank = new BankAccount();
            bank.setBic("BICETCOLEGRAM");
            bank.setBankName("donnelessous");
            bank.setIban("IBANDONNELESSOUS");

    }

    @Test
    void addBankTest() {
        // given
        when(bankRepository.save(any(BankAccount.class))).thenReturn(bank);
        // When
        bankService.addBank(bank);
        // Then
        verify(bankRepository, times(1)).save(bank);
    }

}