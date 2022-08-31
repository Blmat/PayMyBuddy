package com.projet6opcr.paymybuddy.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "banks")
@Data
public class Bank {

    public Bank() {
    }

    public Bank(User userId, String bankName, String iban, String bic) {
        this.userId = userId;
        this.bankName = bankName;
        this.iban = iban;
        this.bic = bic;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bankId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_Id")
    private User userId;

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "iban")
    private String iban;

    @Column(name = "bic")
    private String bic;

}
