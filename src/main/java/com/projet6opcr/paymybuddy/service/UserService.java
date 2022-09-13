package com.projet6opcr.paymybuddy.service;

import com.projet6opcr.paymybuddy.model.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {

    Optional<User> findByEmail(String email);

    void saveUser(User user);

    User findUserById(int id);

    void deleteUserById(Integer id);

    boolean existsByEmail(String email);

}
