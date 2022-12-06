package com.projet6opcr.paymybuddy.controller;

import com.projet6opcr.paymybuddy.exception.GenericNotFoundException;
import com.projet6opcr.paymybuddy.model.BankAccount;
import com.projet6opcr.paymybuddy.model.Transaction;
import com.projet6opcr.paymybuddy.model.UserAccount;
import com.projet6opcr.paymybuddy.model.dto.BankAccountDTO;
import com.projet6opcr.paymybuddy.model.dto.BuddyDTO;
import com.projet6opcr.paymybuddy.model.dto.TransactionDTO;
import com.projet6opcr.paymybuddy.model.dto.UserDTO;
import com.projet6opcr.paymybuddy.service.TransactionService;
import com.projet6opcr.paymybuddy.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MainController {

    private final UserService userService;

    private final TransactionService transactionService;

    @ModelAttribute("transfer")
    public TransactionDTO transactionDTO() {
        return new TransactionDTO();
    }


    @GetMapping("transfer")
    public String getTransfert(Model model) {

        final var buddies = userService.getConnectedUser()
                .getFriends()
                .stream()
                .map(BuddyDTO::new)
                .collect(Collectors.toSet());
        model.addAttribute("friends", buddies);
        return "transfer";
    }

    @GetMapping("contact")
    public String getContact() {
        return "contact";
    }

    @GetMapping("banktransfer")
    public String getBankTransfer(@ModelAttribute("email") String email, Model model) {

        UserAccount user = userService.getConnectedUser();
        BankAccount bankAccount = userService.getConnectedUser().getBank();
        model.addAttribute("bankAttributes", bankAccount);
        model.addAttribute("userBalance", user);

        return "bank_transfer";
    }

    @PostMapping("/bankTransfer")//todo ne fonctionne pas
    public String bankTransfer(@NotNull @ModelAttribute("transferType") String transferType, @NotNull @ModelAttribute("amount") Double amount, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        log.debug("internal transfer ");

        if (!result.hasErrors()) {
            try {

                if (transferType.equals("bank")) {
                    userService.debitMoney(amount);
                } else {
                    userService.addMoney(amount);
                }

                log.debug("You have transferred   : " + amount);
                redirectAttributes.addFlashAttribute("message", "You have transferred   : " + amount);
                return "redirect:/banktransfer";
            } catch (Exception e) {
                log.error(e.getMessage(), e.getCause());
                model.addAttribute("addError", e.getMessage());
            }
        }
        log.error(result.getAllErrors().toString());
        return "bank_transfer";

    }


    @PostMapping("transfer") //todo ne fonctionne pas
    public String transferToSomeone(@Valid @ModelAttribute("transation") TransactionDTO transactionDTO, BindingResult result, Model model, RedirectAttributes redirectAttributes) {

        log.debug("Transfer to buddy " + transactionDTO.getCreditorEmail());

        if (!result.hasErrors()) {
            try {
                transactionService.sendMoney(friendEmail, transactionDTO);
                redirectAttributes.addFlashAttribute("message", "the transfer was made successfully!");
                return "redirect:/transfer";
            } catch (Exception e) {
                log.error(e.getMessage(), e.getCause());
                model.addAttribute("transferError", e.getMessage());
            }
        }
        return "transfer";
    }

}
