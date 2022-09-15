package com.projet6opcr.paymybuddy.service.implementation;

import com.projet6opcr.paymybuddy.model.BankAccount;
import com.projet6opcr.paymybuddy.model.User;
import com.projet6opcr.paymybuddy.repository.BankRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BankServiceImplTest {

    @InjectMocks
    BankServiceImpl bankService;
    @Mock
    BankRepository bankRepository;

    @Mock
    UserServiceImpl userService;

    static BankAccount bank;
    static User userMock;

    @BeforeAll
    static void setUp() throws Exception {
        userMock = new User();
        userMock.setId(1);
        userMock.setFirstName("Jacob");
        userMock.setLastName("Boyd");
        userMock.setEmail("jboy@email.com");

        bank = new BankAccount();
        bank.setUser_id(userMock);
        bank.setBic("BICETCOLEGRAM");
        bank.setAccountName("FrodonCompteCourant");
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

    @Test
    void checkIfUserIfBankAccountExistsTest() {

        //Given
        when(bankRepository.findById(userMock.getId())).thenReturn(Optional.ofNullable(bank));
        // When
        Boolean response = bankService.checkIfUserIfBankAccountExists(userMock.getId());
        // Then
        assertThat(response).isTrue();

    }

}