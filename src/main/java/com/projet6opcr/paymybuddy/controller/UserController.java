package com.projet6opcr.paymybuddy.controller;

import com.projet6opcr.paymybuddy.model.dto.UserDTO;
import com.projet6opcr.paymybuddy.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/profile")
public class UserController {

    private final UserService userService;

    @ModelAttribute("user")
    public UserDTO userRegistrationDTO() {
        return new UserDTO();
    }

    @PostMapping("/addFriend")
    public String addFriend(@RequestParam("friendEmail") String email){
        userService.addFriend(email);
        return "buddy";
    }

    @GetMapping(value = "/addBalance")
    public String addBalance (@RequestParam("amount") String email,Double amount){
        userService.addMoney(email, amount);
        return "home";
    }
}
