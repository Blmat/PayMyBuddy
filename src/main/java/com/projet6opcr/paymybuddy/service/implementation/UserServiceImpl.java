package com.projet6opcr.paymybuddy.service.implementation;

import com.projet6opcr.paymybuddy.model.User;
import com.projet6opcr.paymybuddy.repository.UserRepository;
import com.projet6opcr.paymybuddy.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void saveUser(User user) {
        if (user != null) {
            String firstName = user.getFirstName();
            String lastName = user.getLastName();
            String userMail = user.getEmail();

            String encodedPassword = new BCryptPasswordEncoder().encode(user.getPassword());
            user.setPassword(encodedPassword);

            userRepository.save(user);
            log.info(
                    "[User service] Created a new user with the following information : Mail={} firstName={} lastName={}",
                    userMail, firstName, lastName);
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(userRepository.findByEmail(email));
    }

    @Override
    public User findUserById(int id) {
        return userRepository.findById(id);
    }

    @Override
    public void deleteUserById(Integer id) {
        userRepository.deleteById(id);
    }

    /**
     * This method returns true if an address mail exists in database
     *
     * @param email String : Mail address
     * @return A boolean set to true if the mail address has been found
     */
    @Override
    public boolean existsByEmail(String email) {
        return (userRepository.findByEmail(email) != null);
    }
}
