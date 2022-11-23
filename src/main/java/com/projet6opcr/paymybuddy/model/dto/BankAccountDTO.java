package com.projet6opcr.paymybuddy.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankAccountDTO {

    @NotEmpty
    private String bankName;
    @NotEmpty
    private String iban;
    @NotEmpty
    private String bic;
}
