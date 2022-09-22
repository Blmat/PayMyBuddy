package com.projet6opcr.paymybuddy.service.implementation;

import com.projet6opcr.paymybuddy.exception.UserNotFoundException;
import com.projet6opcr.paymybuddy.model.BankAccount;
import com.projet6opcr.paymybuddy.model.UserAccount;
import com.projet6opcr.paymybuddy.repository.UserRepository;
import com.projet6opcr.paymybuddy.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserAccount addFriend(String friendEmail) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<UserAccount> currentUser = userRepository.findByEmail(username);

        if (currentUser.isPresent()) {
            Set<UserAccount> contacts = currentUser.get().getFriends();
            Optional<UserAccount> friend = userRepository.findByEmail(friendEmail);
            if (friend.isPresent()) {
                contacts.add((UserAccount) friend.get().getFriends());
                currentUser.get().setFriends(contacts);
            }
        } else {
           log.error("User not found");
        }
        return userRepository.save(currentUser.get());
    }


    @Override
    public void saveUser(UserAccount userAccount) {
        if (userAccount != null) {

            String encodedPassword = new BCryptPasswordEncoder().encode(userAccount.getPassword());
            userAccount.setPassword(encodedPassword);

            userRepository.save(userAccount);
            log.info(
                    "[User service] Created a new user with the following information : Mail={} firstName={} lastName={}",
                    userMail, firstName, lastName);
        }
    }

    @Override
    public BankAccount addBankAccount(Integer userId, @Valid BankAccount bankAccount) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("UserAccount not found with id = " + userId));
        user.setBank(bankAccount);
        user = userRepository.save(user);
        log.info(
                "[Bank service] Created a new bank account");

        return user.getBank();
    }

    /**
     * Cette méthode sert à ajouter de l'argent sur le compte d'un utilisateur
     *
     * @param userMail String : le mail de la personne qui se connecte
     * @param amount   Double : l'argent à mettre sur le compte
     * @return la somme qu'il y a sur le compte de l'utilisateur.
     */
    @Override
    public @NotBlank Double addMoney(String userMail, Double amount) {

        var user = userRepository.findByEmail(userMail)
                .orElseThrow(() -> new UserNotFoundException("UserAccount not found with this email = " + userMail));
        user.setBalance(amount);
        user = userRepository.save(user);
        log.info(
                "[Bank service] Money are added.");

        return user.getBalance();
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String email = authentication.getName();
//        Optional<UserAccount> currentUser = userRepository.findByEmail(email);
//
//        currentUser.get().setBalance(currentUser.get().getBalance() + amount);
//        userRepository.save(currentUser);
    }

    /**
     * Cette méthode sert à transférer de l'argent sur le compte d'un utilisateur
     *
     * @param amount Double : l'argent à mettre sur le compte
     * @return la somme qu'il y a sur le compte de l'utilisateur.
     */
    @Override
    public void transferMoney(Double amount) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<UserAccount> currentUserAccount = userRepository.findByEmail(username);

        if (currentUserAccount.isPresent() && currentUserAccount.get().getBalance() - amount >= 0)
            currentUserAccount.get().setBalance(currentUserAccount.get().getBalance() - amount);
        else {
            log.error("Insufficient balance");
            return;
        }
        userRepository.save(currentUserAccount.get());

    }

    /**
     * This method returns true if an address mail exists in database
     *
     * @param email String : Mail address
     * @return A boolean set to true if the mail address has been found
     */
    @Override
    public boolean existsByEmail(String email) {
        return (userRepository.findByEmail(email).isPresent());
    }

    @Override
    public Optional<UserAccount> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<UserAccount> findUserById(Integer id) {
        return userRepository.findById(id);
    }

    @Override
    public void deleteUserById(Integer id) {
        userRepository.deleteById(id);
    }

    @Override
    public Set<UserAccount> getUsers() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<UserAccount> currentUser = userRepository.findByEmail(username);

        return currentUser.get().getFriends();
    }
}
