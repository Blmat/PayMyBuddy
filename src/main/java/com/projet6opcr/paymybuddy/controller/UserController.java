package com.projet6opcr.paymybuddy.controller;

import com.projet6opcr.paymybuddy.model.UserAccount;
import com.projet6opcr.paymybuddy.repository.UserRepository;
import com.projet6opcr.paymybuddy.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;
import java.util.Set;

@Controller
@Slf4j
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping(value = "/")
    public String showHome(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<UserAccount> currentUser = userRepository.findByEmail(username);
        Double balance = currentUser.get().getBalance();

        model.addAttribute("balance", balance);
        return "home";
    }

    @GetMapping(value = "/addFriend")
    public String addFriend(@RequestParam("friendEmail") String email) {
        userService.addFriend(email);
        return "redirect:/contact";
    }

    @GetMapping(value = "/contact")
    public String showTransactions(Model model) {
        Set<UserAccount> users = userService.getUsers();

        model.addAttribute("users", users);
        return "contact";
    }
}