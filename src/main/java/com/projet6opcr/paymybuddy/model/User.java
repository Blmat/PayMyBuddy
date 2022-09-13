package com.projet6opcr.paymybuddy.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Set;

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

    /******************Ajout d'un ami***********************************/
    @ManyToMany
    @JoinTable(
            name = "relation",
            joinColumns = @JoinColumn(name = "owner"),
            inverseJoinColumns = @JoinColumn(name = "buddy"))

    private Set<User> friends;
    public void setFriends(Set<User> friends) {
        this.friends = friends;
    }
    public Set<User> getFriends() {
        return friends;
    }
    /******************************************************************/

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

