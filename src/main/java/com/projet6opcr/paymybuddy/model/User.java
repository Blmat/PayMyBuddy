package com.projet6opcr.paymybuddy.model;

import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@Table(name = "users")
public class User implements UserDetails {

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
    @JoinColumn(name = "iban")
    private BankAccount bankId;

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
    /********************************************************************/

    /********* Ce constructeur est utilisé pour enregistrer un utilisateur****/
    public User(String email, String password, String firstName, String lastName) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }
    /**********************************************************************/


    /********************Constructeur pour la connexion********************/
    public User(User user) {
        this.email = user.getEmail();
        this.password = user.getPassword();
    }
    /**********************************************************************/


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

    /**
     * @return
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    /** UserDetails username est utilisé avec l'email*/
    @Override
    public String getUsername() {
        return email;
    }


    @Override
    public boolean isAccountNonExpired() {
        return false;
    }


    @Override
    public boolean isAccountNonLocked() {
        return false;
    }


    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }


    @Override
    public boolean isEnabled() {
        return false;
    }
}

