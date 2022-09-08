package com.projet6opcr.paymybuddy.service;

import com.projet6opcr.paymybuddy.dto.UserDTO;
import com.projet6opcr.paymybuddy.exception.UserNotFoundException;
import com.projet6opcr.paymybuddy.model.User;
import com.projet6opcr.paymybuddy.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.constraintvalidators.bv.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.Id;
import java.util.*;
import java.util.regex.Pattern;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public User saveUser(UserDTO userDTO) {

        User user = new User(null, null, userDTO.getFirstname(),
                userDTO.getLastname(), userDTO.getEmail(), passwordEncoder.encode(userDTO.getPassword()), (double) 0, null);

        return userRepository.save(user);

    }

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(userRepository.findByEmail(email));
//        return Optional.ofNullable(userRepository.findByEmail(email)
//                .orElseThrow(() -> new UserNotFoundException("Sorry, this user doesn't exist")));
    }

    @Override
    public void addFriend(String friendEmail) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User currentUser = userRepository.findByEmail(username);

        Set<User> contacts = currentUser.getFriends();

        User friend = userRepository.findByEmail(friendEmail);
        contacts.add(friend);
        currentUser.setFriends(contacts);
        userRepository.save(currentUser);
    }

    @Override
    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public boolean existsByEmail(String email) {
        return false;
    }


    @Override
    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }
}
