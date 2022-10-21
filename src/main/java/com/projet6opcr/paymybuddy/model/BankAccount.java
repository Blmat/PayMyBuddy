package com.projet6opcr.paymybuddy.model;

import com.projet6opcr.paymybuddy.model.dto.BankAccountDTO;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.Valid;
import java.util.Objects;

@Entity
@Table(name = "bank_account")
@Getter
@Setter
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
public class BankAccount extends @Valid BankAccountDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "iban",nullable = false)
    private String iban;

    @Column(name = "bank_name")
    private String accountName;

    @Column(name = "bic")
    private String bic;


    public BankAccount(BankAccountDTO bankAccountDTO) {
        this.iban = bankAccountDTO.getIban();
        this.accountName = bankAccountDTO.getBankName();
        this.iban = bankAccountDTO.getIban();
    }

    public void getBank(BankAccountDTO bankAccountDTO) {
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
