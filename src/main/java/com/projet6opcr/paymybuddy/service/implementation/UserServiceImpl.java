package com.projet6opcr.paymybuddy.service.implementation;

import com.projet6opcr.paymybuddy.model.User;
import com.projet6opcr.paymybuddy.repository.UserRepository;
import com.projet6opcr.paymybuddy.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

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
//        return Optional.ofNullable(userRepository.findByEmail(email)
//                .orElseThrow(() -> new UserNotFoundException("Sorry, this user doesn't exist")));
    }

    @Override
    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public void deleteUserById(Long id) {
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


    @Override
    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }
}
