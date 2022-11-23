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

@Controller
@Slf4j
@RequiredArgsConstructor
public class LoginController {

    private final UserService userService;

    @ModelAttribute("user")
    public UserDTO userRegistrationDTO() {
        return new UserDTO();
    }

    @GetMapping("/")
    public String rootPage(Model model) {
        log.debug("Access home page");
        return "login";
    }

    @GetMapping("/login")
    public String getLog() {
        return "login";
    }

    @GetMapping("/disconnect")
    public String getDisconnected() {
        return "redirect:/login?disconnect";
    }


    @GetMapping("/registration")
    public String getRegistration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String registerUserAccount(@Valid @ModelAttribute("user") UserDTO user, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        log.debug("Add a new user account");

        if (!result.hasErrors()) {
            try {
                userService.saveUser(user);
                log.debug("A new user Account has been created : " + user.toString());
                redirectAttributes.addFlashAttribute("message", "Successfully registered, you may now login");
                return "redirect:/login";
            } catch (Exception e) {
                log.error(e.toString(), e.getCause());
                model.addAttribute("signupError", e.getMessage());
            }
        }
        return "registration";
    }
}
