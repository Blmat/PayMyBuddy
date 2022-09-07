package com.projet6opcr.paymybuddy.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "banks")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Bank {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "iban")
    private String iban;

    @Column(name = "bic")
    private String bic;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Bank bank = (Bank) o;
        return bank != null && Objects.equals(bank, bank.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
