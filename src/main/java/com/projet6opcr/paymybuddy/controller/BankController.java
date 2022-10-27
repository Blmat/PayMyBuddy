package com.projet6opcr.paymybuddy.controller;

import com.projet6opcr.paymybuddy.model.UserAccount;
import com.projet6opcr.paymybuddy.model.dto.BankAccountDTO;
import com.projet6opcr.paymybuddy.model.dto.UserDTO;
import com.projet6opcr.paymybuddy.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@Slf4j
@RequiredArgsConstructor
public class BankController {

    private final UserService userService;

    @ModelAttribute("bank")
    public BankAccountDTO bankDTOAdded() {
        return new BankAccountDTO();
    }

    @GetMapping("/profile")
    public String getProfile() {
        return "profile";
    }

    @PostMapping("/profile")
    public String addBankAccount(@ModelAttribute("bank") BankAccountDTO bankAccountDTO, Errors errors) {
        log.debug("Add bank account");

        if (errors.hasErrors()) {
            log.error(errors.toString());
            return "redirect:/profile?error";
        }
        userService.addBankAccount(bankAccountDTO);
        log.debug("the bank is now attached to the account : " + bankAccountDTO.toString());
        return "redirect:/profile?success";
    }
    @GetMapping("/bank")
    public String getBankTransfer() {
        return "bankTransfer";
    }

}
