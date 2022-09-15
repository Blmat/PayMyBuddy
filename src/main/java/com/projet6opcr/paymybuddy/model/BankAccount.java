package com.projet6opcr.paymybuddy.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "bank_account")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bank_id;

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "iban")
    private String iban;

    @Column(name = "bic")
    private String bic;

    @JoinColumn(name = "user_id")
    @OneToOne(cascade = CascadeType.ALL)
    private User user_id;

    public BankAccount(User userId, String bankName, String iban, String bic) {
        this.user_id = userId;
        this.bankName = bankName;
        this.iban = iban;
        this.bic = bic;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        BankAccount bank = (BankAccount) o;
        return bank != null && Objects.equals(bank, bank.iban);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
