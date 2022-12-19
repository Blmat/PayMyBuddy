package com.projet6opcr.paymybuddy.service.implementation;

import com.projet6opcr.paymybuddy.exception.EmailAlreadyExistingException;
import com.projet6opcr.paymybuddy.exception.GenericNotFoundException;
import com.projet6opcr.paymybuddy.exception.InsufficientBalanceException;
import com.projet6opcr.paymybuddy.exception.UserNotFoundException;
import com.projet6opcr.paymybuddy.model.BankAccount;
import com.projet6opcr.paymybuddy.model.UserAccount;
import com.projet6opcr.paymybuddy.model.dto.BankAccountDto;
import com.projet6opcr.paymybuddy.model.dto.UserDto;
import com.projet6opcr.paymybuddy.repository.UserRepository;
import com.projet6opcr.paymybuddy.service.PrincipalUser;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserAccountServiceImplTest {

    UserServiceImpl userService;
    @Mock
    UserRepository userRepositoryMock;
    @Mock
    PrincipalUser principalUser;
    @Mock
    PasswordEncoder passwordEncoder;

    static UserAccount userAccount1;
    static UserAccount buddy1;
    static UserAccount copy;

    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl(userRepositoryMock, principalUser, passwordEncoder);
        BankAccount bankAccount = new BankAccount(1, "IBANBANKACCOUNT1", "NAMEBANKACCOUNT1", "BICBANKACCOUNT1");

        userAccount1 = new UserAccount();
        userAccount1.setUserId(1);
        userAccount1.setFirstName("Jacob");
        userAccount1.setLastName("Boyd");
        userAccount1.setEmail("jboy@email.com");
        userAccount1.setPassword("123456");
        userAccount1.setBalance(BigDecimal.valueOf(10.0));
        userAccount1.setBank(bankAccount);


        buddy1 = new UserAccount();
        buddy1.setUserId(2);
        buddy1.setFirstName("Tenley");
        buddy1.setLastName("Boyd");
        buddy1.setEmail("tenley@email.com");
        buddy1.setPassword("123456");
        buddy1.setBalance(BigDecimal.valueOf(0.0));

        copy = new UserAccount();
        copy.setUserId(3);
        copy.setFirstName("John");
        copy.setLastName("Wick");
        copy.setEmail("jboy@email.com");
        copy.setPassword("123456");
        copy.setBalance(BigDecimal.valueOf(0.0));
    }

    /******************************addFriendTest******************************/
    @Test
    @DisplayName("ajout d'un ami existant dans la BDD")
    void addFriendTest() {
        //GIVEN
        //WHEN
        when(principalUser.getCurrentUserOrThrowException()).thenReturn(userAccount1);
        when(userRepositoryMock.findByEmail(buddy1.getEmail())).thenReturn(Optional.of(buddy1));
        when(userRepositoryMock.save(any())).thenAnswer(i -> i.getArguments()[0]);

        var response = userService.addFriend(buddy1.getEmail());

        // Then
        assertThat(response)
                .satisfies(u -> {
                    assertThat(u.getUserId()).isEqualTo(userAccount1.getUserId());
                    assertThat(u.getFriends()).contains(buddy1);
                });
    }

    @Test
    @DisplayName("Test retourne une erreur car le personne essaye de s'ajouter en temps qu'ami")
    void identicalEmailTest_throwException() {
        //GIVEN
        //WHEN
        when(principalUser.getCurrentUserOrThrowException()).thenReturn(userAccount1);

        var response = assertThrows(GenericNotFoundException.class, () -> userService.addFriend(userAccount1.getEmail()));
        assertThat(response).hasMessage("You can't add yourself ");
    }

    @Test
    @DisplayName("ce test doit retourner une erreur car la personne n'existe pas dans la BDD")
    void addFriendErrorTest() {
        //GIVEN
        //WHEN
        when(principalUser.getCurrentUserOrThrowException()).thenReturn(userAccount1);

        assertThrows(UserNotFoundException.class, () -> userService.addFriend("emailNonTrouvé@email.fr"));
    }

    /***********************************************************************************************************/

    @Test
    void getConnectedUserTest() {
        when(principalUser.getCurrentUserOrThrowException()).thenReturn(userAccount1);

        var response = userService.getConnectedUser();

        assertThat(response)
                .satisfies(r -> {
                    assertThat(r).isNotNull();
                    assertThat(r).isEqualTo(userAccount1);
                });
    }


    @Test
    @DisplayName("Ce test doit retourner User1 qui à était trouvé grâce à son id")
    void findUserByIdTest() {

        // When
        when(userRepositoryMock.findById(1)).thenReturn(Optional.of(userAccount1));

        // Then
        var response = userService.findUserById(1);

        assertThat(response)
                .satisfies(u -> {
                    assertThat(u).isPresent();
                    assertThat(u.get().getUserId()).isEqualTo(1);
                    assertThat(u.get().getEmail()).isEqualTo("jboy@email.com");
                    assertThat(u.get().getFirstName()).isEqualTo("Jacob");
                });
    }

    @Test
    void saveUserTest() {
        //Give
        var newUser = new UserDto(userAccount1.getFirstName(), userAccount1.getLastName(), userAccount1.getEmail(), userAccount1.getPassword());

        // When
        when(userRepositoryMock.save(any())).thenAnswer(i -> i.getArguments()[0]);

        // Then
        var response = userService.saveUser(newUser);


        verify(userRepositoryMock, times(1)).save(response);

        assertThat(response.getFirstName()).isEqualTo(userAccount1.getFirstName());
        assertThat(response.getLastName()).isEqualTo(userAccount1.getLastName());
        assertThat(response.getEmail()).isEqualTo(userAccount1.getEmail());
        assertThat(response.getPassword()).isEqualTo(passwordEncoder.encode(userAccount1.getPassword()));
        assertThat(response.getBalance()).isEqualByComparingTo(BigDecimal.ZERO);
        assertThat(response.getBank()).isNull();

    }

    @Test
    @DisplayName("retourne une erreur car la personne utilise une adresse mail déjà enregistré dans la BDD")
    void error_sameEmail_Test() {
        //Give
        var user = new UserDto(userAccount1.getFirstName(), userAccount1.getLastName(), userAccount1.getEmail(), userAccount1.getPassword());

        // When
        when(userRepositoryMock.existsByEmail(user.getEmail())).thenReturn(true);

        // Then
        var response = assertThrows(EmailAlreadyExistingException.class, () -> userService.saveUser(user));
    }

    @Test
    @DisplayName("findByEmail test Ok")
    void findByEmailTest() {
        // When
        when(userRepositoryMock.findByEmail(anyString())).thenReturn(Optional.of(userAccount1));
        // Then
        var response = userService.findByEmail("jboy@email.com");

        assertThat(response).isPresent();
        assertThat(response.get().getEmail()).isEqualTo("jboy@email.com");
        assertThat(response.get().getUserId()).isEqualTo(1);
        assertThat(response.get().getLastName()).isEqualTo("Boyd");
    }

    @Test
    @DisplayName("Ne trouve pas une personne grâce à son email")
    void findByEmailNotFoundTest() {
        // Then
        var response = userService.findByEmail("jboy@email.com");

        assertThat(response).isNotPresent();
    }

    @Test
    void deleteUserByIdTest() {
        // Then
        userService.deleteUserById(userAccount1.getUserId());

        verify(userRepositoryMock, times(1)).deleteById(userAccount1.getUserId());
    }

    /***************************************addBankAccountTest**************************************************************/
    @Test
    @DisplayName("ajout d'un compte bancaire d'une personne existante dans la BDD")
    void addBankAccountTest() {
        //Given
        when(principalUser.getCurrentUserOrThrowException()).thenReturn(userAccount1);
        final var bankAccountDto = new BankAccountDto("IBANBANKACCOUNT1", "NAMEBANKACCOUNT1", "BICBANKACCOUNT1");

        //WHEN
        when(userRepositoryMock.save(any())).thenAnswer(i -> i.getArguments()[0]);

        var response = userService.addBankAccount(bankAccountDto);

        //Then
        Assertions.assertThat(response)
                .satisfies(u -> {
                    Assertions.assertThat(userAccount1.getUserId()).isEqualTo(1);
                    Assertions.assertThat(userAccount1.getBalance()).isEqualByComparingTo(BigDecimal.valueOf(10.0));
                });
    }

    @Test
    @DisplayName("Personne non présente dans la BDD")
    void addBankAccountErrorTest() {
        when(principalUser.getCurrentUserOrThrowException()).thenThrow(new UserNotFoundException("UserAccount not found with id = 1"));

        final var bankAccountDto = new BankAccountDto("IBANBANKACCOUNT1", "NAMEBANKACCOUNT1", "BICBANKACCOUNT1");

        var response = assertThrows(UserNotFoundException.class, () -> userService.addBankAccount(bankAccountDto));

        assertThat(response).hasMessage("UserAccount not found with id = 1");
    }

    /**************************************add Money Test********************************************************/
    @Test
    @DisplayName("ajout de money sur un compte existant")
    void addMoneyTest() {
        //GIVEN
        when(principalUser.getCurrentUserOrThrowException()).thenReturn(userAccount1);
        when(userRepositoryMock.save(any())).thenAnswer(i -> i.getArguments()[0]);
        //WHEN
        var response = userService.addMoney(userAccount1.getBalance());

        //THEN
        Assertions.assertThat(response)
                .isNotNull()
                .satisfies(u -> {
                    Assertions.assertThat(userAccount1.getEmail()).isEqualTo(userAccount1.getEmail());
                    Assertions.assertThat(userAccount1.getBalance()).isEqualTo(userAccount1.getBalance());
                });
    }

    @Test
    @DisplayName("ajout de money sur un compte non existant")
    void addMoneyErrorTest() {
        //WHEN
        when(principalUser.getCurrentUserOrThrowException()).thenThrow(new UserNotFoundException("KO"));

        var response = assertThrows(UserNotFoundException.class, () -> userService.addMoney(userAccount1.getBalance()));

        assertThat(response).hasMessage("KO");
    }

    /**************************************transfert Money Test********************************************************/
    @Test
    @DisplayName("transfert d'argent avec suffisamment de fond")
    void transfertMoneyTest() {
        //WHEN
        when(principalUser.getCurrentUserOrThrowException()).thenReturn(userAccount1);
        when(userRepositoryMock.findByEmail(buddy1.getEmail())).thenReturn(Optional.of(buddy1));

        when(userRepositoryMock.save(userAccount1)).thenAnswer(i -> i.getArguments()[0]);

        userService.transferMoney(buddy1.getEmail(), BigDecimal.valueOf(5.0));

        verify(userRepositoryMock, times(1)).save(userAccount1);
        assertThat(userAccount1.getBalance()).isEqualByComparingTo(BigDecimal.valueOf(5.0));
    }

    @Test
    @DisplayName("transfert d'argent avec fond insuffisant")
    void transfertMoneyErrorTest() {
        when(principalUser.getCurrentUserOrThrowException()).thenReturn(userAccount1);
        when(userRepositoryMock.findByEmail(buddy1.getEmail())).thenReturn(Optional.of(buddy1));

        var response = assertThrows(InsufficientBalanceException.class, () -> userService.transferMoney(buddy1.getEmail(), BigDecimal.valueOf(20.0)));
        assertThat(response).hasMessage("sorry you don't have enough money ");
    }
}