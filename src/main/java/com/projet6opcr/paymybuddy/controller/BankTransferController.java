package com.projet6opcr.paymybuddy.controller;

import com.projet6opcr.paymybuddy.model.dto.BankAccountDTO;
import com.projet6opcr.paymybuddy.model.dto.BuddyDTO;
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
import java.util.Optional;
import java.util.stream.Collectors;

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
    public String getProfile(Model model) {
        var bankAttributes = Optional.ofNullable(userService.getConnectedUser().getBank())
                .map(BankAccountDTO::new)
                .orElse(new BankAccountDTO());
        model.addAttribute("bankAttributes", bankAttributes);

        return "profile";
    }

    @PostMapping("/profile")
    public String addBankAccount(@Valid @ModelAttribute("bank") BankAccountDTO bankAccountDTO, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        log.debug("Add a new bank account");

        if (!result.hasErrors()) {
            try {
                userService.addBankAccount(bankAccountDTO);
                log.debug("the bank is now attached to the account : " + bankAccountDTO.toString());
                redirectAttributes.addFlashAttribute("message", "bank added successfully");
                return "redirect:/profile";
            } catch (Exception e) {
                log.error(e.getMessage(), e.getCause());
                model.addAttribute("bankError", e.getMessage());
            }
        }
        return "/profile";
    }
}
