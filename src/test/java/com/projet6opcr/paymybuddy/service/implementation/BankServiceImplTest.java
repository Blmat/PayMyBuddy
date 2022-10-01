//package com.projet6opcr.paymybuddy.service.implementation;
//
//import com.projet6opcr.paymybuddy.exception.BankAccountNotFoundException;
//import com.projet6opcr.paymybuddy.exception.UserNotFoundException;
//import com.projet6opcr.paymybuddy.model.BankAccount;
//import com.projet6opcr.paymybuddy.model.UserAccount;
//import com.projet6opcr.paymybuddy.repository.BankRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.assertj.core.api.Assertions.assertThatThrownBy;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//class BankServiceImplTest {
//
//    @InjectMocks
//    BankServiceImpl bankService;
//    @Mock
//    BankRepository bankRepository;
//    static BankAccount bank;
//    static UserAccount userMock;
//
//    @BeforeEach
//    void init() throws Exception {
//        userMock = new UserAccount();
//        userMock.setId(1);
//        userMock.setFirstName("Jacob");
//        userMock.setLastName("Boyd");
//        userMock.setEmail("jboy@email.com");
//
//        bank = new BankAccount();
//        bank.setId(userMock.getId());
//        bank.setBic("BICETCOLEGRAM");
//        bank.setAccountName("FrodonCompteCourant");
//        bank.setIban("IBANDONNELESSOUS");
//
//    }
//
//
//
//    @Test
//    void checkIfBankAccountUserExistsTest() {
//        //Given
//        when(bankRepository.findById(userMock.getId())).thenReturn(Optional.ofNullable(bank));
//        // When
//        var response = bankService.checkIfBankAccountUserExists(userMock.getId());
//        // Then
//        assertThat(response).isPresent();
//        assertThat(response).contains(bank);
//
//
//    }
//
//    @Test
//    void checkIfBankAccountUserExists_fail_Test() {
//        // When
//        assertThrows(BankAccountNotFoundException.class, () ->bankService.checkIfBankAccountUserExists(1));
//
//
//    }
//}