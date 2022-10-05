package com.projet6opcr.paymybuddy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/")
    public String index() {
        return "login";
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @GetMapping("/error")
    public String error() {
        return "error";
    }

    @GetMapping("/home")
    public String getHome() {
        return "/home";
    }

    @GetMapping("/profile")
    public String getProfile() {
        return "/profile";
    }

    @GetMapping("/transfert")
    public String getTransfert() {
        return "/transaction";
    }

    @GetMapping("contact")
    public String getContact() {
        return "/contact";
    }
    }
