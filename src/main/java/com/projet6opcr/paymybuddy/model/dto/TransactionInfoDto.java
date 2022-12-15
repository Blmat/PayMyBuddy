package com.projet6opcr.paymybuddy.model.dto;

import com.projet6opcr.paymybuddy.model.Transaction;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDate;

@Value
public class TransactionInfoDto {

    Integer id;
    String creditor;
    BigDecimal amount;
    String reason;
    LocalDate date;

    public TransactionInfoDto(Transaction transaction) {
        this.id = transaction.getId();
        this.creditor = transaction.getCreditor().getFirstName() + " " + transaction.getCreditor().getLastName();
        this.amount = transaction.getAmount();
        this.reason = transaction.getReason();
        this.date = transaction.getDate();
    }
}
