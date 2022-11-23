package com.projet6opcr.paymybuddy.controller;

import com.projet6opcr.paymybuddy.model.dto.BankAccountDTO;
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
public class BankTransferController {

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
    public String addBankAccount(@Valid @ModelAttribute("bank") BankAccountDTO bankAccountDTO, BindingResult result) {
        log.debug("Add a new bank account");
        if (!result.hasErrors()) {
            try {
                userService.addBankAccount(bankAccountDTO);
                log.debug("the bank is now attached to the account : " + bankAccountDTO.toString());
                return "redirect:/profile?success";
            } catch (Exception e) {
                log.error(e.getMessage(), e.getCause());
            }
        }
        return "redirect:/profile?error";
    }
}
