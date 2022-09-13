package com.projet6opcr.paymybuddy.service.implementation;

import com.projet6opcr.paymybuddy.model.User;
import com.projet6opcr.paymybuddy.repository.UserRepository;
import com.projet6opcr.paymybuddy.service.RelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class RelationServiceImpl implements RelationService {

    @Autowired
    private UserRepository userRepository;

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
}
