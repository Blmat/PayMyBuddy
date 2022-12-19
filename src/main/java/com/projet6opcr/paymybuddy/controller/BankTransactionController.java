package com.projet6opcr.paymybuddy.controller;

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

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Controller
@Slf4j
@RequiredArgsConstructor
public class BankTransactionController {

    private final UserService userService;

    @GetMapping("contact")
    public String getContact() {
        return "contact";
    }

    @GetMapping("bank_transfer")
    public String getBankTransfer(@ModelAttribute("email") String email, Model model) {

        final var user = userService.getConnectedUser();
        final var bankAccount = userService.getConnectedUser().getBank();

        model.addAttribute("bankAttributes", bankAccount);
        model.addAttribute("userBalance", user);

        return "bank_transaction";
    }

    @PostMapping("/bankTransfer")
    public String bankTransfer(@NotNull @ModelAttribute("BankName") String bankName,
                               @NotNull @ModelAttribute("transferType") String transferType,
                               @NotNull @ModelAttribute("amount") BigDecimal amount,
                               BindingResult result, Model model, RedirectAttributes redirectAttributes) {

        log.debug("internal transfer ");

        final var bankAccount = userService.getConnectedUser().getBank();

        if (!result.hasErrors()) {
            try {
                if (!bankName.equals(bankAccount.getAccountName())) {
                    redirectAttributes.addFlashAttribute("error", "Please enter your bank name");
                    return "redirect:/bank_transfer";
                }
                if (amount.signum() < 1) {
                    redirectAttributes.addFlashAttribute("error", "Sorry but money can't be = or < to 0");
                    return "redirect:/bank_transfer";
                }
                if (transferType.equals("debit")) {
                    userService.debitMoney(amount);
                } else {
                    userService.addMoney(amount);
                }


                log.debug("You have transferred   : " + amount + "€");
                redirectAttributes.addFlashAttribute("message", "You have transferred   : " + amount + "€");
                return "redirect:/bank_transfer";
            } catch (Exception e) {
                log.error(e.getMessage(), e.getCause());
                model.addAttribute("addError", e.getMessage());
            }
        }
        log.error(result.getAllErrors().toString());
        return "bank_transaction";

    }
}
