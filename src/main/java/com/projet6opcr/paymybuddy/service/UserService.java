package com.projet6opcr.paymybuddy.service;

import com.projet6opcr.paymybuddy.model.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {

    Optional<User> findByEmail(String email);
    void saveUser(User user);

    Optional<User> findUserById(Long id);
    void deleteUserById(Long id);

    boolean existsByEmail(String email);

     List<User> findAll();
}
