package com.projet6opcr.paymybuddy.model;

import com.projet6opcr.paymybuddy.configuration.constant.Commission;
import com.projet6opcr.paymybuddy.exception.InsufficientBalanceException;
import com.projet6opcr.paymybuddy.model.dto.UserDTO;
import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Table

public class UserAccount implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @Column(nullable = false)
    @NotNull
    @NotBlank
    private String firstName;

    @Column(nullable = false)
    @NotNull
    @NotBlank
    private String lastName;

    @Column(nullable = false, unique = true)
    @NotNull
    @Email
    @NotBlank
    private String email;

    @Column(nullable = false)
    @NotNull
    @NotBlank
    private String password;

    @Column(nullable = false)
    private Double balance = .0;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private BankAccount bank;

    /******************Ajout d'un ami***********************************/
    @ManyToMany(cascade = CascadeType.PERSIST)
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
        this.balance = .0;
    }

    /********************Constructeur pour la connexion********************/
    public UserAccount(UserAccount userAccount) {
        this.email = userAccount.getEmail();
        this.password = userAccount.getPassword();
    }

    public UserAccount(@NotNull String lastname, @javax.validation.constraints.NotNull String firstname, @Email @NotNull String email, @NotNull String password, @NotNull double balance) {
        super();
        this.lastName = lastname;
        this.firstName = firstname;
        this.email = email;
        this.password = password;
        this.balance = balance;
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

        return debitBalanceAmount(Math.ceil(amountWithCommission * 100) / 100.0); // permet d'arrondir au centième
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
        return userId != null && Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public double roundingAfterTheDecimalPoint(double value) {
        BigDecimal bd = BigDecimal.valueOf(value).setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

}

