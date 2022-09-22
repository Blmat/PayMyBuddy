package com.projet6opcr.paymybuddy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/login_ap")
    public String login(Model model) {

        model.addAttribute("name", "blabla");

    @GetMapping("/home")
    public String home() {
        return "home";
    }
}
