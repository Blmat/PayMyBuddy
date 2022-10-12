package com.projet6opcr.paymybuddy.model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class UserAccount implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "firstname")
    @NotNull
    @NotBlank
    private String firstName;

    @Column(name = "lastname")
    @NotNull
    @NotBlank
    private String lastName;

    @Column(name = "email", unique = true, nullable = false)
    @NotNull
    @Email
    @NotBlank
    private String email;

    @Column(name = "password", nullable = false)
    @NotNull
    @NotBlank
    private String password;

    @Column(name = "balance",nullable = false)
    @NotNull
    @NotBlank
    private Double balance;

    @OneToOne
    @JoinColumn(name = "bank_id")
    @NotBlank
    @NotNull
    private BankAccount bank;

    /******************Ajout d'un ami***********************************/
    @ManyToMany
    @JoinTable(
            name = "relation",
            joinColumns = @JoinColumn(name = "owner"),
            inverseJoinColumns = @JoinColumn(name = "buddy"))

    private Set<UserAccount> friends;
    public void setFriends(Set<UserAccount> friends) {
        this.friends = friends;
    }
    public Set<UserAccount> getFriends() {
        return friends;
    }
    /********************************************************************/

    /********* Ce constructeur est utilisé pour enregistrer un utilisateur****/
    public UserAccount(String email, String password, String firstName, String lastName) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }
    /**********************************************************************/


    /********************Constructeur pour la connexion********************/
    public UserAccount(UserAccount userAccount) {
        this.email = userAccount.getEmail();
        this.password = userAccount.getPassword();
    }
    /**********************************************************************/


    @OneToMany(fetch = FetchType.LAZY,mappedBy = "owner")      // Table relation
    private List<Relation> listRelations;


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

