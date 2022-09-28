package com.projet6opcr.paymybuddy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/log")
    public String login(Model model) {

        model.addAttribute("name", "blabla");

        return "login_ap";
    }

    @GetMapping("/*")
    public String index() {
        return "index";
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }
}
