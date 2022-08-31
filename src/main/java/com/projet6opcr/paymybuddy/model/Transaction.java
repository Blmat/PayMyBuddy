package com.projet6opcr.paymybuddy.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "transactions")
public class Transaction {

    public Transaction() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "transaction_id", nullable = false)
    private Long transactionId;

    public Transaction( LocalDate date, double amount, String reason) {
        this.date = date;
        this.amount = amount;
        this.reason = reason;
    }
    @Column(name = "date")
    private LocalDate date;

    @Column(name = "amount")
    private double amount;

    @Column(name = "reason")
    private String reason;

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

}

