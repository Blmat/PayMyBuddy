package com.projet6opcr.paymybuddy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String index() {
        return "login";
    }

    @GetMapping("/profile")
    public String getProfile() {
        return "profile";
    }

    @GetMapping("/transfert")
    public String getTransfert() {
        return "transfer";
    }

    @GetMapping("contact")
    public String getContact() {
        return "contact";
    }

    @GetMapping("/buddy")
    public String getBuddy() {
        return "buddy";
    }

    @GetMapping("/banktransfer")
    public String getBankTransfer() {
        return "bankTransfer";
    }
}
