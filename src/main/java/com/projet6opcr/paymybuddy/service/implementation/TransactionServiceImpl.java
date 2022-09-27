package com.projet6opcr.paymybuddy.service.implementation;

import com.projet6opcr.paymybuddy.constant.COMMISSION;
import com.projet6opcr.paymybuddy.dto.TransactionDTO;
import com.projet6opcr.paymybuddy.model.Transaction;
import com.projet6opcr.paymybuddy.model.UserAccount;
import com.projet6opcr.paymybuddy.repository.TransactionRepository;
import com.projet6opcr.paymybuddy.repository.UserRepository;
import com.projet6opcr.paymybuddy.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Transaction> getTransactions() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<UserAccount> currentUser = userRepository.findByEmail(username);
        List<Transaction> transactions = transactionRepository.findAll();

        return transactions.stream()
                .filter(c -> (c.getDebtor().equals(currentUser.get().getId()))
                        ||
                        c.getCreditor().equals(currentUser.get().getId()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void sendMoney(TransactionDTO transactionDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<UserAccount> currentUser = userRepository.findByEmail(username);

        Optional<UserAccount> friend = userRepository.findByEmail(String.valueOf(transactionDTO.getReceiverId()));
        Transaction transaction = new Transaction(currentUser.get().getId(), friend.get().getId(),
                LocalDate.now(), transactionDTO.getAmount(), transactionDTO.getReason());
        if (currentUser.get().getBalance() - (transactionDTO.getAmount() + (transactionDTO.getAmount() * COMMISSION.TRANSACTION_COMMISSION)) >= 0)
            currentUser.get().setBalance(currentUser.get().getBalance() - (transactionDTO.getAmount() + (transactionDTO.getAmount() * 0.005)));
        else
            log.error("Insufficient balance");
        friend.get().setBalance(friend.get().getBalance() + transactionDTO.getAmount());

        transactionRepository.save(transaction);
    }
}
