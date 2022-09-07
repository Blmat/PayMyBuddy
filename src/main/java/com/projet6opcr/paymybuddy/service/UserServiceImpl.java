package com.projet6opcr.paymybuddy.service;

import com.projet6opcr.paymybuddy.dto.UserDTO;
import com.projet6opcr.paymybuddy.model.User;
import com.projet6opcr.paymybuddy.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{

    private UserRepository userRepository;

    @Override
    public Optional<User> findByEmail(String email) {
//        return userRepository.findByEmail(email).orElseThrow(()-> new UserNotFoundException("Sorry, this user doesn't exist"));
        return null;
//                Optional.ofNullable(userRepository.findByEmail(email));
    }

    @Override
    public User saveUser(UserDTO userDTO) {
//        return userRepository.saveUser(userDTO);
        return null;
    }

    @Override
    public void addFriend(String friendEmail) { //Todo faire cette methode

    }

    @Override
    public Optional<User> findUserById(Long id) {
//        return userRepository.findById(id);
        return null;
    }

    @Override
    public void deleteUserById(Long id) {
//         userRepository.deleteById(id);
    }
}
