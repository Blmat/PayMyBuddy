package com.projet6opcr.paymybuddy.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "lastname")
    private String lastName;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "balance",nullable = false)
    private Double balance;

    @OneToOne(mappedBy = "user_id")
    @JoinColumn(name = "bank_id")
    private Bank bankId;


//    @ManyToMany
//    @JoinTable(
//            name = "relation",
//            joinColumns = @JoinColumn(name = "buddy"),
//            inverseJoinColumns = @JoinColumn(name = "owner"))

//    private Set<User> relation;
//    public Set<User> getRelation() {
//        return relation;
//    }
//    public void setFriends(Set<User> relation) {
//        this.relation = relation;
//    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return false;
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

