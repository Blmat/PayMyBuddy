package com.projet6opcr.paymybuddy.service.implementation;

import com.projet6opcr.paymybuddy.exception.UserNotFoundException;
import com.projet6opcr.paymybuddy.model.UserAccount;
import com.projet6opcr.paymybuddy.repository.UserRepository;
import com.projet6opcr.paymybuddy.service.ConnectedUserDetailsService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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
    void deleteUserByIdTest() {


        // Then
        userService.deleteUserById(userAccount1.getId());

        verify(userRepositoryMock, times(1)).deleteById(userAccount1.getId());
    }

    @Test
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
}