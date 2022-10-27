package com.projet6opcr.paymybuddy.controller;

import com.projet6opcr.paymybuddy.model.dto.UserDTO;
import com.projet6opcr.paymybuddy.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @ModelAttribute("friend")
    public UserDTO friendRegistration() {
        return new UserDTO();
    }

    @GetMapping(value = "/addBalance")
    public String getBalance(Double amount) {
        userService.addMoney( amount);
        return "home";
    }

    @GetMapping("/buddy")
    public String getBuddy() {
        return "buddy";
    }

    @PostMapping("/add_buddy")
    public String addFriend(String email, Errors result) {

        if (result.hasErrors()) {
            return "redirect:/buddy?error";
        }
        userService.addFriend(email);
        return "redirect:/buddy?success";
    }
}
