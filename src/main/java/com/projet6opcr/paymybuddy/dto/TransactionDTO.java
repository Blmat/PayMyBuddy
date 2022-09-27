package com.projet6opcr.paymybuddy.dto;

import com.projet6opcr.paymybuddy.model.UserAccount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDTO {

    private double amount;
    private String reason;
    private LocalDate date;
    private UserAccount receiverId;
    private UserAccount senderId;

}
