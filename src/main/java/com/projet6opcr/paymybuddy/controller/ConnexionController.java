package com.projet6opcr.paymybuddy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ConnexionController {

    @GetMapping("/logoff")
    public String getDisconnected() {
        return "redirect:/login?disconnect";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
