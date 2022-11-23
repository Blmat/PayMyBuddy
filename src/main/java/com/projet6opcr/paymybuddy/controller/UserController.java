package com.projet6opcr.paymybuddy.controller;

import com.projet6opcr.paymybuddy.model.dto.UserDTO;
import com.projet6opcr.paymybuddy.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @ModelAttribute("friend")
    public UserDTO friendRegistration() {
        return new UserDTO();
    }

    @GetMapping(value = "/add_balance")
    public String getBalance(Double amount) {
        userService.addMoney(amount);
        return "home";
    }

    @GetMapping("/buddy")
    public String getBuddy() {
        return "buddy";
    }

    @PostMapping("/buddy")
    public String addFriend(@ModelAttribute("friendEmail") String email, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        log.debug("Add a new buddy");

        if (!result.hasErrors()) {
            try {
                userService.addFriend(email);
                log.debug("You added a new buddy   : " + email);
                redirectAttributes.addFlashAttribute("message", "New registered buddy!");
                return "/buddy";
            } catch (Exception e) {
                log.error(e.getMessage(), e.getCause());
                model.addAttribute("addError", e.getMessage());
            }
        }
        return "redirect:/buddy";
    }
}