package com.projet6opcr.paymybuddy.model;

import com.projet6opcr.paymybuddy.configuration.constant.Commission;
import com.projet6opcr.paymybuddy.exception.InsufficientBalanceException;
import com.projet6opcr.paymybuddy.model.dto.UserDto;
import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Entity
@Getter
@Setter
@ToString
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
    private BigDecimal balance = BigDecimal.valueOf(.0);

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
    public UserAccount(UserDto userDTO) {
        this.firstName = userDTO.getFirstName();
        this.lastName = userDTO.getLastName();
        this.email = userDTO.getEmail();
        this.password = userDTO.getPassword();
        this.balance = BigDecimal.valueOf(.0);
    }

    /********************Constructeur pour la connexion********************/
    public UserAccount(UserAccount userAccount) {
        this.email = userAccount.getEmail();
        this.password = userAccount.getPassword();
    }

    public UserAccount(@NotNull String lastname, @NotNull String firstname, @Email @NotNull String email, @NotNull String password, @NotNull BigDecimal balance) {
        super();
        this.lastName = lastname;
        this.firstName = firstname;
        this.email = email;
        this.password = password;
        this.balance = balance;
    }


    public BigDecimal getBalance() {
        return this.balance.setScale(2, RoundingMode.HALF_UP);
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


    /**
     * Sert à retirer de l'argent sur le compte d'un utilisateur vers sa banque
     *
     * @param amount = l'argent à retirer
     */
    public void debitBalanceAmount(BigDecimal amount) {

        if (amount.signum() <= 0) {
            throw new IllegalArgumentException("Amount can not be null ot negative");
        }

        if (this.balance.subtract(amount).signum() < 0) {
            throw new InsufficientBalanceException("sorry you don't have enough money ");
        }

        this.balance = this.balance.subtract(amount);

    }


    /**
     * Sert à retirer de l'argent sur le compte d'un utilisateur et à calculer la commission qui sera prélevée
     * afin de pouvoir l'envoyer sur le compte d'un ami
     *
     * @param transaction = le montant de la somme à transférer.
     */
    public void debitBalanceAmount(Transaction transaction) {

        var amount = transaction.getAmount();
        var commission = transaction.getCommission();

        if (commission.signum() < 0) {
            throw new IllegalArgumentException("Commission can not be negative");
        }

        var amountWithCommission = amount.add(amount.multiply(Commission.TRANSACTION_COMMISSION));

        debitBalanceAmount(amountWithCommission);
    }


    /**
     * Sert à ajouter de l'argent sur le compte d'un utilisateur depuis sa banque
     *
     * @param amount = l'argent à ajouter.
     */
    public void creditBalanceAmount(BigDecimal amount) {

        if (amount.signum() <= 0) {
            throw new IllegalArgumentException("Amount can not be null ot negative");
        }

        this.balance = this.balance.add(amount);
    }


    /**
     * Cette méthode sert à récuperer l'argent pour ensuite l'ajouter sur solde de la personne choisie.
     *
     * @param transaction= le montant, la raison, le débiteur et créditeur de la transaction à effectuer.
     */
    public void creditBalanceAmount(Transaction transaction) {

        var amount = transaction.getAmount();
        creditBalanceAmount(amount);
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

}

