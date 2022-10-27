package com.projet6opcr.paymybuddy.controller;

import com.projet6opcr.paymybuddy.model.dto.UserDTO;
import com.projet6opcr.paymybuddy.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
public class ConnexionController {

    private final UserService userService;

    @ModelAttribute("user")
    public UserDTO userRegistrationDTO() {
        return new UserDTO();
    }


    @GetMapping("/")
    public String getLog() {
        return "login";
    }

    @GetMapping("/logoff")
    public String getDisconnected() {
        return "redirect:/?disconnect";
    }


    @GetMapping("/registration")
    public String getRegistration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String registerUserAccount(UserDTO user, Errors result) {
        log.debug("Add a new user account");

        if (result.hasErrors()) {
            log.error(result.toString());
            return "redirect:/registration?error";
        }
        userService.saveUser(user);
        log.debug("A new user Account has been created : " + user.toString());
        return "redirect:/?success";
    }
}
