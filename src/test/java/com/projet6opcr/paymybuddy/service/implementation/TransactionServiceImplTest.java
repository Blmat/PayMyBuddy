package com.projet6opcr.paymybuddy.service.implementation;

import com.projet6opcr.paymybuddy.model.dto.TransactionDTO;
import com.projet6opcr.paymybuddy.exception.InsufficientBalanceException;
import com.projet6opcr.paymybuddy.model.BankAccount;
import com.projet6opcr.paymybuddy.model.UserAccount;
import com.projet6opcr.paymybuddy.repository.TransactionRepository;
import com.projet6opcr.paymybuddy.repository.UserRepository;
import com.projet6opcr.paymybuddy.service.PrincipalUser;
import com.projet6opcr.paymybuddy.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {
    TransactionService transactionService;
    @Mock
    TransactionRepository transactionRepository;
    @Mock
    UserRepository userRepository;
    @Mock
    PrincipalUser principalUser;

    static UserAccount userCreditor;
    static UserAccount userDebtor;
    TransactionDTO transaction1;
    TransactionDTO transaction2;

    BankAccount bankAccount1;
    BankAccount bankAccount2;

    @BeforeEach
    void setUp() {
        bankAccount1 = new BankAccount("IBANBANKACCOUNT1", "NAMEBANKACCOUNT1", "BICBANKACCOUNT1");
        bankAccount2 = new BankAccount("IBANBANKACCOUNT2", "NAMEBANKACCOUNT2", "BICBANKACCOUNT2");
        userCreditor = new UserAccount(1, "Jacob", "Boyd", "jBoy@email.com", "456", 30.0,bankAccount1, null);
        userDebtor = new UserAccount(2, "John", "Boyd", "johnBoy@email.com", "123", 20.0, bankAccount2, null);
        transaction1 = new TransactionDTO(10.0, "achat smartphone");
        transaction2 = new TransactionDTO(19.91, "achat chargeur de smartphone");

        transactionService = new TransactionServiceImpl(transactionRepository, userRepository, principalUser);
    }

    @Test
    @DisplayName("transaction ok car il y a suffisamment de fond")
    void sendMoneyTest() {
        /*initialisation de la personne qui va recevoir l'argent*/
        lenient().when(userRepository.findByEmail(userCreditor.getEmail())).thenReturn(Optional.ofNullable(userCreditor));
        when(userRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);

        /*initialisation de la personne qui va donner l'argent*/
        when(principalUser.getCurrentUserOrThrowException()).thenReturn(userDebtor);
        lenient().when(userRepository.findByEmail(userDebtor.getEmail())).thenReturn(Optional.of(userDebtor));
        lenient().when(userRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);

        transactionService.sendMoney(userCreditor.getEmail(), transaction1);

        verify(userRepository, times(1)).findByEmail(userCreditor.getEmail());
    }

    @Test
    @DisplayName("Erreur à la transaction  car il y a pas suffisamment de fond")
    void sendMoneyErrorTest(){

        lenient().when(userRepository.findByEmail(userCreditor.getEmail())).thenReturn(Optional.ofNullable(userCreditor));
        when(userRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);

        when(principalUser.getCurrentUserOrThrowException()).thenReturn(userDebtor);
        lenient().when(userRepository.findByEmail(userDebtor.getEmail())).thenReturn(Optional.of(userDebtor));
        lenient().when(userRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);

        var response = assertThrows(InsufficientBalanceException.class, () -> transactionService.sendMoney(userCreditor.getEmail(), transaction2));

        assertThat(response).hasMessage("sorry you don't have enough money ");
    }

}