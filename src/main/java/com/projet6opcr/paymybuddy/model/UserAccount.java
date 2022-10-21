package com.projet6opcr.paymybuddy.model;

import com.projet6opcr.paymybuddy.constant.Commission;
import com.projet6opcr.paymybuddy.exception.InsufficientBalanceException;
import com.projet6opcr.paymybuddy.model.dto.BankAccountDTO;
import com.projet6opcr.paymybuddy.model.dto.UserDTO;
import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.*;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
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

    @Column(name = "email", nullable = false, unique = true)
    @NotNull
    @Email
    @NotBlank
    private String email;

    @Column(name = "password", nullable = false)
    @NotNull
    @NotBlank
    private String password;

    @Column(name = "balance", nullable = false)
    private Double balance = .0;

    @OneToOne
    @JoinColumn(name = "bank_id")
    private BankAccount bank;

    /******************Ajout d'un ami***********************************/
    @ManyToMany
    @JoinTable(
            name = "relation",
            joinColumns = @JoinColumn(name = "owner"),
            inverseJoinColumns = @JoinColumn(name = "buddy"))
    @ToString.Exclude

    private Set<UserAccount> friends = new HashSet<>();

    /********* Ce constructeur est utilisé pour enregistrer un utilisateur****/
    public UserAccount(UserDTO userDTO) {
        this.firstName = userDTO.getFirstName();
        this.lastName = userDTO.getLastName();
        this.email = userDTO.getEmail();
        this.password = userDTO.getPassword();
    }

    /********************Constructeur pour la connexion********************/
    public UserAccount(UserAccount userAccount) {
        this.email = userAccount.getEmail();
        this.password = userAccount.getPassword();
    }

    /**********************************************************************/


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    /**
     * UserDetails username est utilisé avec l'email
     */
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



    public Double debitBalanceAmount(Double amount) {

        if (amount <= 0) {
            throw new IllegalArgumentException("Amount can not be null ot negative");
        }

        if (this.balance - amount < 0) {
            throw new InsufficientBalanceException("sorry you don't have enough money ");
        }

        this.balance = this.balance - amount;

        return this.balance;
    }


    public Double debitBalanceAmount(Transaction transaction) {

        var amount = transaction.getAmount();
        var commission = transaction.getCommission();

        if (commission < 0) {
            throw new IllegalArgumentException("Commission can not be negative");
        }

        var amountWithCommission = amount + (amount * Commission.TRANSACTION_COMMISSION);

        return debitBalanceAmount(amountWithCommission);
    }


    public Double creditBalanceAmount(Double amount) {

        if (amount <= 0) {
            throw new IllegalArgumentException("Amount can not be null ot negative");
        }

        this.balance = this.balance + amount;
        return this.balance;
    }


    public Double creditBalanceAmount(Transaction transaction) {

        var amount = transaction.getAmount();
        return creditBalanceAmount(amount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UserAccount that = (UserAccount) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public void setBank(BankAccountDTO bankAccount) {
    }
}

