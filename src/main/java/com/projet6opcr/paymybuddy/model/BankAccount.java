package com.projet6opcr.paymybuddy.model;

import com.projet6opcr.paymybuddy.model.dto.BankAccountDTO;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table
@Getter
@Setter
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String iban;

    @Column
    private String accountName;

    @Column
    private String bic;

    public BankAccount(BankAccountDTO bankAccountDto) {
        this.iban = bankAccountDto.getIban();
        this.accountName = bankAccountDto.getBankName();
        this.bic = bankAccountDto.getBic();
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
