package com.projet6opcr.paymybuddy.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
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
    private Long id;

    @ManyToOne
    private User creditor;

    @ManyToOne
    private User debtor;

    @Column(name="amount")
    private double amount;

    @Column(name="reason")
    private String reason;

    @Column (name= "date")
    private LocalDate date;


    @Column (name="commission")
    private Double commission;

    public Transaction(Long id, Long id1, LocalDate now, double amount, String reason) {
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

