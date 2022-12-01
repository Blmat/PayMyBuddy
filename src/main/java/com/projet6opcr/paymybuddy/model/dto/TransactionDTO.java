package com.projet6opcr.paymybuddy.model.dto;

import com.projet6opcr.paymybuddy.model.UserAccount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDTO {

    private double amount;
    private String reason;

}
