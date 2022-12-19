package com.projet6opcr.paymybuddy.controller;


import com.projet6opcr.paymybuddy.model.dto.BankAccountDto;
import com.projet6opcr.paymybuddy.model.dto.BuddyDto;
import com.projet6opcr.paymybuddy.model.dto.UserDto;
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

import java.util.stream.Collectors;

@Slf4j
@Controller
@RequiredArgsConstructor
public class FriendController {

    private final UserService userService;

    @GetMapping("/buddy")
    public String getBuddy(Model model) {
        final var buddies = userService.getConnectedUser()
                .getFriends()
                .stream()
                .map(BuddyDto::new)
                .collect(Collectors.toSet());
        model.addAttribute("buddies", buddies);
        return "add_buddy";
    }

    @PostMapping("/buddy")
    public String addFriend(@ModelAttribute("email") String email, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        log.debug("Add a new buddy");

        if (!result.hasErrors()) {
            try {
                userService.addFriend(email);
                log.debug("You added a new buddy   : " + email);
                redirectAttributes.addFlashAttribute("message", "New registered buddy! {" + email + " }");
                return "redirect:/buddy";
            } catch (Exception e) {
                log.error(e.getMessage(), e.getCause());
                model.addAttribute("addError", e.getMessage());
            }
        }
        return "add_buddy";
    }
}