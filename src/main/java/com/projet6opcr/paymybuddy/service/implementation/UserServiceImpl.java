package com.projet6opcr.paymybuddy.service.implementation;

import com.projet6opcr.paymybuddy.exception.InsufficientBalanceException;
import com.projet6opcr.paymybuddy.exception.UserNotFoundException;
import com.projet6opcr.paymybuddy.model.BankAccount;
import com.projet6opcr.paymybuddy.model.UserAccount;
import com.projet6opcr.paymybuddy.model.dto.BankAccountDTO;
import com.projet6opcr.paymybuddy.model.dto.UserDTO;
import com.projet6opcr.paymybuddy.repository.UserRepository;
import com.projet6opcr.paymybuddy.service.PrincipalUser;
import com.projet6opcr.paymybuddy.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PrincipalUser principalUser;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserAccount addFriend(String friendEmail) {
        UserAccount currentUser = principalUser.getCurrentUserOrThrowException();
        var friend = userRepository.findByEmail(friendEmail)
                .orElseThrow(() -> new UserNotFoundException("User not found with email = " + friendEmail));
        currentUser.getFriends().add(friend);
        return userRepository.save(currentUser);
    }


    @Override
    public UserAccount getConnectedUser() {
        return principalUser.getCurrentUserOrThrowException();
    }

    @Override
    public UserAccount saveUser(UserDTO userDTO) {

        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        UserAccount user = new UserAccount(userDTO);

        userRepository.save(user);
        log.info(
                "[UserAccount service] Created a new userAccount with the following information : Mail={} firstName={} lastName={}",
                user.getEmail(), user.getFirstName(), user.getLastName());

        return user;
    }


    public BankAccount addBankAccount(Integer userId, BankAccountDTO bankAccount) {
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
        user.creditBalanceAmount(amount);

        user = userRepository.save(user);
        log.info(
                "[Bank service] Money are added.");

        return user.getBalance();
    }

    /**
     * Cette méthode sert à transférer de l'argent sur le compte d'un utilisateur
     *
     * @param amount Double : l'argent à mettre sur le compte
     */
    @Override
    public void transferMoney(String friendEmail, Double amount) throws InsufficientBalanceException {
        UserAccount currentUserAccount = principalUser.getCurrentUserOrThrowException();
        userRepository.findByEmail(friendEmail)
                .orElseThrow(() -> new UserNotFoundException("User not found with email = " + friendEmail));

        currentUserAccount.debitBalanceAmount(amount);

        userRepository.save(currentUserAccount);
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

}
