package com.projet6opcr.paymybuddy.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankAccountDTO {

    private String bankName;
    private String iban;
    private String bic;
}
