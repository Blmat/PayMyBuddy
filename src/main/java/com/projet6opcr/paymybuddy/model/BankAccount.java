package com.projet6opcr.paymybuddy.model;

import com.sun.istack.NotNull;
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
    @Column(name = "iban",nullable = false)
    private String iban;

    @Column(name = "bank_name")
    @NotNull
    private String accountName;

    @Column(name = "bic")
    @NotNull
    private String bic;


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
