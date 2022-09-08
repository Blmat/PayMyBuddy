package com.projet6opcr.paymybuddy.service;

import com.projet6opcr.paymybuddy.dto.UserDTO;
import com.projet6opcr.paymybuddy.model.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {

    Optional<User> findByEmail(String email);
    User saveUser(UserDTO userDTO);

    void addFriend(String friendEmail);

    Optional<User> findUserById(Long id);
    void deleteUserById(Long id);

    boolean existsByEmail(String email);

     List<User> findAll();
}
