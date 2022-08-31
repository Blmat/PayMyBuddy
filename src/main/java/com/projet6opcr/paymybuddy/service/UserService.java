package com.projet6opcr.paymybuddy.service;

import com.projet6opcr.paymybuddy.model.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    public User findByEmail(String email);
    public User saveUser(User user);

    void deleteUserById(Integer id);
}
