package com.projet6opcr.paymybuddy.controller;

import com.projet6opcr.paymybuddy.model.dto.BuddyDto;
import com.projet6opcr.paymybuddy.model.dto.TransactionDto;
import com.projet6opcr.paymybuddy.service.TransactionService;
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
import java.util.stream.Collectors;

@Controller
@Slf4j
@RequiredArgsConstructor
public class TransferToFriendController {
    private final UserService userService;

    private final TransactionService transactionService;

    @ModelAttribute("transfer")
    public TransactionDto transactionDTO() {
        return new TransactionDto();
    }


    @GetMapping("transfer")
    public String getTransfert(Model model) {

        final var buddies = userService.getConnectedUser()
                .getFriends()
                .stream()
                .map(BuddyDto::new)
                .collect(Collectors.toSet());

        model.addAttribute("friends", buddies);

        final var transactions = transactionService.findAllTransactions();
        model.addAttribute("TransactionInfo", transactions);

        return "friend_transfer";
    }

    @PostMapping("transfer")
    public String transferToSomeone(@Valid @ModelAttribute("transation") TransactionDto transactionDTO, BindingResult result, Model model, RedirectAttributes redirectAttributes) {

        log.debug("Transfer to buddy " + transactionDTO.getCreditorEmail());

        if (!result.hasErrors()) {
            try {
                transactionService.sendMoney(transactionDTO.getCreditorEmail(), transactionDTO);
                redirectAttributes.addFlashAttribute("message", "the transfer was made successfully!");
                return "redirect:/transfer";
            } catch (Exception e) {
                log.error(e.getMessage(), e.getCause());
                model.addAttribute("transferError", e.getMessage());
            }
        }
        return "friend_transfer";
    }
}
