package com.projet6opcr.paymybuddy.controller;

import com.projet6opcr.paymybuddy.service.TransactionService;
import com.projet6opcr.paymybuddy.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MainController {

    private final UserService userService;

    private final TransactionService transactionService;


    @GetMapping("/transfer")//todo faire le get pour afficher les infos
    public String getTransfert() {

        return "transfer";
    }

    @GetMapping("contact")
    public String getContact() {
        return "contact";
    }

    @GetMapping("/banktransfer")//todo donner le nom de lma bank avec les infos
    public String getBankTransfer(Model model) {
//        var bankAccount = Optional.ofNullable(userService.getConnectedUser().getBank())
//                .map(BankAccountDTO::new)
//                .orElse(new BankAccountDTO());
//        model.addAttribute("bank", bankAccount);

        return "bank_transfer";
    }
}
