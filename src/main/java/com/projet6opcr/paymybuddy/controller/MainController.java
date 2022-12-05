package com.projet6opcr.paymybuddy.controller;

import com.projet6opcr.paymybuddy.model.UserAccount;
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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Set;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MainController {

    private final UserService userService;

    private final TransactionService transactionService;


    @GetMapping("/transfer")
    public String getTransfert(Model model) {

        UserAccount friends = userService.getConnectedUser();
        model.addAttribute("friends", friends);
        return "transfer";
    }

    @GetMapping("contact")
    public String getContact() {
        return "contact";
    }

    @GetMapping("/banktransfer")//todo donner le nom de lma bank avec les infos
    public String getBankTransfer(Model model) {

//        UserAccount friends = userService.getConnectedUser();
//        model.addAttribute("bankAttributes", friends);

        return "bank_transfer";
    }





}
