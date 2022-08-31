package com.projet6opcr.paymybuddy.dto;

import lombok.Data;

@Data
public class BankAccountDTO {

    private String bankName;
    private String iban;
    private String bic;

    public BankAccountDTO(){
    }

    public BankAccountDTO(String bankName, String iban, String bic) {
        this.bankName = bankName;
        this.iban = iban;
        this.bic = bic;
    }
}
