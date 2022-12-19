package com.projet6opcr.paymybuddy.model.dto;

import com.projet6opcr.paymybuddy.model.BankAccount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankAccountDto {

    @NotEmpty
    private String bankName;
    @NotEmpty
    private String iban;
    @NotEmpty
    private String bic;

    public BankAccountDto(BankAccount bankAccount) {
        bankName = bankAccount.getAccountName();
        iban = bankAccount.getIban();
        bic = bankAccount.getBic();
    }
}
