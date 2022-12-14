package com.projet6opcr.paymybuddy.model;

import com.projet6opcr.paymybuddy.configuration.constant.Commission;
import com.projet6opcr.paymybuddy.model.dto.TransactionDto;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "creditor")
    private UserAccount creditor;

    @ManyToOne
    @JoinColumn(name = "debtor")
    private UserAccount debtor;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "reason")
    private String reason;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "commission")
    private BigDecimal commission = Commission.TRANSACTION_COMMISSION;

    public Transaction(UserAccount debtor, UserAccount creditor, TransactionDto transactionDTO, BigDecimal commission) {
        if (transactionDTO.getAmount().signum() <= 0) {
            throw new IllegalArgumentException("Transaction amount can not be null ot negative");
        }

        this.debtor = debtor;
        this.creditor = creditor;
        this.amount = transactionDTO.getAmount();
        this.reason = transactionDTO.getReason();
        this.date = LocalDate.now();
        this.commission = commission;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Transaction that = (Transaction) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

