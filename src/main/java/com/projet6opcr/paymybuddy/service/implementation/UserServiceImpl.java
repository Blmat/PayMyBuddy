package com.projet6opcr.paymybuddy.service.implementation;

import com.projet6opcr.paymybuddy.exception.EmailAlreadyExistingException;
import com.projet6opcr.paymybuddy.exception.GenericNotFoundException;
import com.projet6opcr.paymybuddy.exception.InsufficientBalanceException;
import com.projet6opcr.paymybuddy.exception.UserNotFoundException;
import com.projet6opcr.paymybuddy.model.BankAccount;
import com.projet6opcr.paymybuddy.model.UserAccount;
import com.projet6opcr.paymybuddy.model.dto.BankAccountDto;
import com.projet6opcr.paymybuddy.model.dto.UserDto;
import com.projet6opcr.paymybuddy.repository.UserRepository;
import com.projet6opcr.paymybuddy.service.PrincipalUser;
import com.projet6opcr.paymybuddy.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Objects;
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
        if (Objects.equals(friendEmail, currentUser.getEmail())) {
            throw new GenericNotFoundException("You can't add yourself ");
        }

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
    public UserAccount saveUser(UserDto userDTO) {

        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new EmailAlreadyExistingException("Email already used");
        }

        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        UserAccount user = new UserAccount(userDTO);

        userRepository.save(user);
        log.info(
                "[UserAccount service] Created a new userAccount with the following information : Mail={} firstName={} lastName={}",
                user.getEmail(), user.getFirstName(), user.getLastName());

        return user;
    }


    public BankAccount addBankAccount(BankAccountDto bankAccountDto) {

        var user = principalUser.getCurrentUserOrThrowException();
        user.setBank(new BankAccount(bankAccountDto));
        userRepository.save(user);
        log.info(
                "[Bank service] Created a new bank account with the following information : Bank name={} IBAN={} BIC={}",
                bankAccountDto.getBankName(), bankAccountDto.getIban(), bankAccountDto.getBic());

        return user.getBank();
    }

    /**
     * Cette méthode sert à ajouter de l'argent sur le compte d'un utilisateur depuis sa banque.
     *
     * @param amount BigDecimal : l'argent à mettre sur le compte
     * @return la somme qu'il y a sur le compte de l'utilisateur.
     */
    @Override
    public BigDecimal addMoney(BigDecimal amount) {
        var user = principalUser.getCurrentUserOrThrowException();
        user.creditBalanceAmount(amount);

        user = userRepository.save(user);
        log.info("[Bank service] Money are added.");

        return user.getBalance();
    }

    @Override
    public BigDecimal debitMoney(BigDecimal amount) {
        var user = principalUser.getCurrentUserOrThrowException();
        user.debitBalanceAmount(amount);

        user = userRepository.save(user);
        log.info("[Bank service] the money was withdrawn.");

        return user.getBalance();
    }

    /**
     * Cette méthode sert à transférer de l'argent sur le compte d'un ami
     *
     * @param amount BigDecimal : l'argent à mettre sur le compte
     */
    @Override
    public void transferMoney(String friendEmail, BigDecimal amount) throws InsufficientBalanceException {
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
