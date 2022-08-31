package com.projet6opcr.paymybuddy.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Data
public class User {
    public User() {
    }

    public User(String firstName, String lastName, String email, String password, Double balance/*, Set<User> friends*/, Bank bankId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.balance = balance;
//        this.friends = friends;
        this.bankId = bankId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @OneToOne(mappedBy = "userId")
    @JoinColumn(name = "bank_Id")
    private Bank bankId;

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "lastname")
    private String lastName;

    @Column(name = "email", length = 50)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "balance")
    private Double balance;
}
