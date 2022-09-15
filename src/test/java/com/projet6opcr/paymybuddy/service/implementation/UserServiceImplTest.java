package com.projet6opcr.paymybuddy.service.implementation;

import com.projet6opcr.paymybuddy.model.User;
import com.projet6opcr.paymybuddy.repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserRepository userRepositoryMock;

    static User user1;
    static User buddy1;

    static User john;

    @BeforeAll
    static void setUp(){
        user1 = new User();
        user1.setId(1);
        user1.setFirstName("Jacob");
        user1.setLastName("Boyd");
        user1.setEmail("jboy@email.com");
        user1.setPassword(new BCryptPasswordEncoder().encode("mdp1"));
        user1.setBalance(10.0);

        buddy1 = new User();
        buddy1.setId(2);
        buddy1.setFirstName("Tenley");
        buddy1.setLastName("Boyd");
        buddy1.setEmail("tenley@email.com");
        buddy1.setPassword(new BCryptPasswordEncoder().encode("mdp2"));
    }

    @Test
    @DisplayName("Ce test doit retourner User1 qui à était trouvé grâce à son id")
    void findUserByIdTest() {
        // Given
        when(userRepositoryMock.findById(1)).thenReturn(user1);
        // When
        User response = userService.findUserById(1);
        // Then
        assertThat(response.getId()).isEqualTo(1);
        assertThat(response.getEmail()).isEqualTo("jboy@email.com");
        assertThat(response.getFirstName()).isEqualTo("Jacob");
    }


    @Test
    void saveUserTest() {
        // given
        when(userRepositoryMock.save(any(User.class))).thenReturn(user1);
        // When
        userService.saveUser(user1);
        // Then
        verify(userRepositoryMock, times(1)).save(user1);

    }

    @Test
    void findByEmailTest() {
        //Given
        when(userRepositoryMock.findByEmail(anyString())).thenReturn(user1);
        // When
        var response = userService.findByEmail("jboy@email.com");
        // Then
        assertThat(response).isPresent();
        assertThat(response.get().getEmail()).isEqualTo("jboy@email.com");
        assertThat(response.get().getId()).isEqualTo(1);
        assertThat(response.get().getLastName()).isEqualTo("Boyd");

    }

    @Test
    void deleteUserByIdTest() {

        // When
        userService.deleteUserById(user1.getId());
        // Then
        verify(userRepositoryMock, times(1)).deleteById(user1.getId());
    }

    @Test
    void existsByEmailTest() {

        john = new User();
        john.setEmail("MrWick@dog.fr");

        //Given
        when(userRepositoryMock.findByEmail(john.getEmail())).thenReturn(john);
        // When
        Boolean response = userService.existsByEmail("MrWick@dog.fr");
        // Then
        assertThat(response).isTrue();
    }
    @Test
    void notExistsByEmailTest() {

        john = new User();
        john.setEmail("MrWick@dog.fr");

        //Given
        when(userRepositoryMock.findByEmail(john.getEmail())).thenReturn(null);
        // When
        Boolean response = userService.existsByEmail("MrWick@dog.fr");
        // Then
        assertThat(response).isFalse();
    }
}