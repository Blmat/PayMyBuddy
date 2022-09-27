package com.projet6opcr.paymybuddy.service.implementation;

import com.projet6opcr.paymybuddy.exception.InsufficientBalanceException;
import com.projet6opcr.paymybuddy.exception.UserNotFoundException;
import com.projet6opcr.paymybuddy.model.UserAccount;
import com.projet6opcr.paymybuddy.repository.UserRepository;
import com.projet6opcr.paymybuddy.service.PrincipalUser;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserAccountServiceImplTest {

    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserRepository userRepositoryMock;

    static UserAccount userAccount1;
    static UserAccount buddy1;

    static UserAccount john;

    @BeforeAll
    static void setUp() {
        userAccount1 = new UserAccount();
        userAccount1.setId(1);
        userAccount1.setFirstName("Jacob");
        userAccount1.setLastName("Boyd");
        userAccount1.setEmail("jboy@email.com");
        userAccount1.setPassword(new BCryptPasswordEncoder().encode("mdp1"));
        userAccount1.setBalance(10.0);

        buddy1 = new UserAccount();
        buddy1.setId(2);
        buddy1.setFirstName("Tenley");
        buddy1.setLastName("Boyd");
        buddy1.setEmail("tenley@email.com");
        buddy1.setPassword(new BCryptPasswordEncoder().encode("mdp2"));
    }

    /******************************addFriendTest******************************/
    @Test
    @DisplayName("ajout d'un ami existant dans la BDD")
    void addFriendTest() {
        //GIVEN
        //WHEN
        when(principalUser.getCurrentUserOrThrowException()).thenReturn(userAccount1);
        lenient().when(userRepositoryMock.findByEmail(userAccount1.getEmail())).thenReturn(Optional.of(buddy1));
        when(userRepositoryMock.save(any())).thenAnswer(i -> i.getArguments()[0]);

        var response = userService.addFriend(userAccount1.getEmail());

        // Then
        Assertions.assertThat(response)
                .satisfies(u -> {
                    Assertions.assertThat(u.getId()).isEqualTo(userAccount1.getId());
                    Assertions.assertThat(u.getFriends()).contains(buddy1);
                });
    }

    @Test
    @DisplayName("ce test doit retourner null car personne n'est trouvé")
    void addFriendNullTest() {
        //GIVEN
        //WHEN
        when(principalUser.getCurrentUserOrThrowException()).thenReturn(userAccount1);
        lenient().when(userRepositoryMock.findByEmail(userAccount1.getEmail())).thenReturn(Optional.of(buddy1));

        var response = userService.addFriend(userAccount1.getEmail());

        // Then
        assertThat(response).isNull();
    }

    @Test
    @DisplayName("ce test doit retourner une erreur car la personne n'existe pas dans la BDD")
    void addFriendErrorTest() {
        //GIVEN
        //WHEN
        when(principalUser.getCurrentUserOrThrowException()).thenReturn(userAccount1);

        assertThrows(UserNotFoundException.class, () -> userService.addFriend(userAccount1.getEmail()));
    }

    /***********************************************************************************************************/

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
                    assertThat(u.get().getId()).isEqualTo(1);
                    assertThat(u.get().getEmail()).isEqualTo("jboy@email.com");
                    assertThat(u.get().getFirstName()).isEqualTo("Jacob");
                });
    }

    @Test
    void saveUserTest() {
        // When
        when(userRepositoryMock.save(any(UserAccount.class))).thenReturn(userAccount1);
        // Then
        userService.saveUser(userAccount1);
        verify(userRepositoryMock, times(1)).save(userAccount1);
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
        assertThat(response.get().getId()).isEqualTo(1);
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
        userService.deleteUserById(userAccount1.getId());

        verify(userRepositoryMock, times(1)).deleteById(userAccount1.getId());
    }

    @Test
    @DisplayName("test OK car la personne existe bien dans la BDD")
    void existsByEmailTest() {
        //Given
        john = new UserAccount();
        john.setEmail("MrWick@dog.fr");
        // When
        when(userRepositoryMock.findByEmail(john.getEmail())).thenReturn(Optional.ofNullable(john));
        // Then
        Boolean response = userService.existsByEmail("MrWick@dog.fr");

        assertThat(response).isTrue();
    }

    @Test
    @DisplayName("retourne faux car la personne n'est pas trouvé")
    void existsByEmailFalseTest() {
        // Then
        Boolean response = userService.existsByEmail("notTheGoodMail@mail.fr");

        assertThat(response).isFalse();
    }

    /***************************************addBankAccountTest**************************************************************/
    @Test
    @DisplayName("ajout d'un compte bancaire d'une personne existante dans la BDD")
    void addBankAccountTest() {
        //WHEN
        lenient().when(userRepositoryMock.findById(userAccount1.getId())).thenReturn(Optional.of(userAccount1));
        when(userRepositoryMock.save(any())).thenAnswer(i -> i.getArguments()[0]);

        var response = userService.addBankAccount(1, userAccount1.getBank());

        //Then
        Assertions.assertThat(response)
                .satisfies(u -> {
                    Assertions.assertThat(userAccount1.getId()).isEqualTo(1);
                    Assertions.assertThat(userAccount1.getBalance()).isEqualTo(10.0);
                });
    }

    @Test
    @DisplayName("Personne non présente dans la BDD")
    void addBankAccountErrorTest() {
        var response = assertThrows(UserNotFoundException.class, () -> userService.addBankAccount(userAccount1.getId(), userAccount1.getBank()));

        assertThat(response).hasMessage("UserAccount not found with id = 1");
    }

    /**************************************add Money Test********************************************************/
    @Test
    @DisplayName("ajout de money sur un compte existant")
    void addMoneyTest() {
        //GIVEN
        lenient().when(userRepositoryMock.findByEmail(userAccount1.getEmail())).thenReturn(Optional.of(userAccount1));
        when(userRepositoryMock.save(any())).thenAnswer(i -> i.getArguments()[0]);
        //WHEN
        var response = userService.addMoney(userAccount1.getEmail(), userAccount1.getBalance());

        //THEN
        Assertions.assertThat(response)
                .satisfies(u -> {
                    Assertions.assertThat(userAccount1.getEmail()).isEqualTo(userAccount1.getEmail());
                    Assertions.assertThat(userAccount1.getBalance()).isEqualTo(userAccount1.getBalance());
                });
    }

    @Test
    @DisplayName("ajout de money sur un compte non existant")
    void addMoneyErrorTest() {
        var response = assertThrows(UserNotFoundException.class, () -> userService.addMoney(userAccount1.getEmail(), userAccount1.getBalance()));

        assertThat(response).hasMessage("UserAccount not found with this email = " + userAccount1.getEmail());
    }

    /**************************************transfert Money Test********************************************************/
    @Test
    @DisplayName("transfert d'argent avec suffisamment de fond")
    void transfertMoneyTest() {
        //WHEN
        when(principalUser.getCurrentUserOrThrowException()).thenReturn(userAccount1);
        lenient().when(userRepositoryMock.findByEmail(buddy1.getEmail())).thenReturn(Optional.of(buddy1));

        when(userRepositoryMock.save(userAccount1)).thenAnswer(i -> i.getArguments()[0]);

        userService.transferMoney(buddy1.getEmail(), 5.0);

        verify(userRepositoryMock, times(1)).save(userAccount1);
        assertThat(userAccount1.getBalance()).isEqualTo(5.0);
    }

    @Test
    @DisplayName("transfert d'argent avec fond insuffisant")
    void transfertMoneyErrorTest() {
        when(principalUser.getCurrentUserOrThrowException()).thenReturn(userAccount1);
        lenient().when(userRepositoryMock.findByEmail(buddy1.getEmail())).thenReturn(Optional.of(buddy1));

        var response = assertThrows(InsufficientBalanceException.class, () -> userService.transferMoney(buddy1.getEmail(), 20.0));
        assertThat(response).hasMessage("sorry you don't have enough money ");
    }
}