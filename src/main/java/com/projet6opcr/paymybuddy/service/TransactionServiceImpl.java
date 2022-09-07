package com.projet6opcr.paymybuddy.service;

import com.projet6opcr.paymybuddy.dto.TransactionDTO;
import com.projet6opcr.paymybuddy.model.Transaction;
import com.projet6opcr.paymybuddy.model.User;
import com.projet6opcr.paymybuddy.repository.TransactionRepository;
//import com.projet6opcr.paymybuddy.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Transactional
public class TransactionServiceImpl implements TransactionService{

    @Autowired
    private TransactionRepository transactionRepository;

//    @Autowired
//    private UserRepository userRepository;


    @Override
    public void transferRequest(TransactionDTO transactionDTO) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String username = authentication.getName();
//        Optional<User> currentUser = userRepository.findByEmail(username);
//
//        Optional<User> friend = userRepository.findByEmail(String.valueOf(transactionDTO.getCreditorId()));
//        Transaction transaction = new Transaction(currentUser.getUserId(), friend.getUserId(),
//                LocalDate.now(), transactionDTO.getAmount(), transactionDTO.getReason());
//        if (currentUser.getBalance() - (transactionDTO.getAmount() + (transactionDTO.getAmount() * 0.005)) >= 0)
//            currentUser.setBalance(currentUser.getBalance() - (transactionDTO.getAmount() + (transactionDTO.getAmount() * 0.005)));
//        else
//            log.error("Insufficient balance");
//        friend.setBalance(friend.getBalance() + transactionDTO.getAmount());
//
//        transactionRepository.save(transaction);
    }

    @Override
    public List<Transaction> getTransactions() {
        return null;
    }
}
