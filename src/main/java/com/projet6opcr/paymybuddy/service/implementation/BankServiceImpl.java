package com.projet6opcr.paymybuddy.service.implementation;

import com.projet6opcr.paymybuddy.dto.BankAccountDTO;
import com.projet6opcr.paymybuddy.model.Bank;
import com.projet6opcr.paymybuddy.model.User;
import com.projet6opcr.paymybuddy.repository.BankRepository;
import com.projet6opcr.paymybuddy.repository.UserRepository;
import com.projet6opcr.paymybuddy.service.BankService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class BankServiceImpl implements BankService {

    @Autowired
    private BankRepository bankRepository;

    @Autowired
    private UserRepository userRepository;



    @Override
    public void addBank(BankAccountDTO bankAccountDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User currentUser = userRepository.findByEmail(username);

        Bank bank = new Bank(currentUser, bankAccountDTO.getBankName(), bankAccountDTO.getIban(),
                bankAccountDTO.getBic());

        bankRepository.save(bank);
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
    public Optional<Bank> getBank(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User currentUser = userRepository.findByEmail(username);

        return bankRepository.findById(currentUser.getId());
    }
}
