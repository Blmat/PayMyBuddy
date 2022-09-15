package com.projet6opcr.paymybuddy.service.implementation;

import com.projet6opcr.paymybuddy.model.BankAccount;
import com.projet6opcr.paymybuddy.model.User;
import com.projet6opcr.paymybuddy.repository.BankRepository;
import com.projet6opcr.paymybuddy.repository.UserRepository;
import com.projet6opcr.paymybuddy.service.BankService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class BankServiceImpl implements BankService {

    private final BankRepository bankRepository;
    private final UserRepository userRepository;


    public BankServiceImpl(BankRepository bankRepository, UserRepository userRepository) {
        this.bankRepository = bankRepository;
        this.userRepository = userRepository;
    }

    public boolean checkIfUserIfBankAccountExists(Integer userId) {
        return (bankRepository.findById(userId).isPresent());
    }
    @Override
    public void addBank(BankAccount bank) {
        if (bank != null) {
            String accountName = bank.getAccountName();
            String bic = bank.getBic();
            String iban = bank.getIban();

            bankRepository.save(bank);
            log.info(
                    "[Bank service] Created a new bank account with the following information : Bank name={} bic={} Iban={}",
                    accountName, bic, iban);
        }


//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String username = authentication.getName();
//        User currentUser = userRepository.findByEmail(username);
//
//        Bank bank1 = new Bank(currentUser, bank.getBankName(), bank.getIban(),
//                bank.getBic());
//
//        bankRepository.save(bank);
    }


    @Override
    public void addMoney(Double amount) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User currentUser = userRepository.findByEmail(username);

        currentUser.setBalance(currentUser.getBalance() + amount);
        userRepository.save(currentUser);

    }


    @Override
    public void transferMoney(Double amount) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User currentUser = userRepository.findByEmail(username);

        if (currentUser.getBalance() - amount >= 0)
            currentUser.setBalance(currentUser.getBalance() - amount);
        else {
            log.error("Insufficient balance");
            return;
        }
        userRepository.save(currentUser);

    }

    @Override
    public Optional<BankAccount> getBank(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User currentUser = userRepository.findByEmail(username);

        return bankRepository.findById(currentUser.getId());
    }
}
