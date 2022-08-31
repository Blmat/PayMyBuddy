package com.projet6opcr.paymybuddy.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TransactionDTO {
    private double amount;
    private String reason;
    private LocalDate date;

    public TransactionDTO() {
    }

    public TransactionDTO(double amount, String reason, LocalDate date) {
        this.amount = amount;
        this.reason = reason;
        this.date = date;
    }
}
