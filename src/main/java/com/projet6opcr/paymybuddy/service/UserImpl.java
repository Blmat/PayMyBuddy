package com.projet6opcr.paymybuddy.service;

import com.projet6opcr.paymybuddy.model.User;
import com.projet6opcr.paymybuddy.repository.UserRepository;

public class UserImpl implements UserService{

    private UserRepository userRepository;

    @Override
    public User findByEmail(String email) {
        return (User) userRepository.findByEmail(email);
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteUserById(Integer id) {
         userRepository.deleteById(id);
    }
}
