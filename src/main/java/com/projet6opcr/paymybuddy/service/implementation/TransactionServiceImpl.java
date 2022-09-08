package com.projet6opcr.paymybuddy.service.implementation;

import com.projet6opcr.paymybuddy.constant.COMMISSION;
import com.projet6opcr.paymybuddy.dto.TransactionDTO;
import com.projet6opcr.paymybuddy.model.Transaction;
import com.projet6opcr.paymybuddy.model.User;
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
        return null;
    }

    @Override
    @Transactional
    public void sendMoney(TransactionDTO transactionDTO){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User currentUser = userRepository.findByEmail(username);

        User friend = userRepository.findByEmail(String.valueOf(transactionDTO.getReceiverId()));
        Transaction transaction = new Transaction(currentUser.getId(), friend.getId(),
                LocalDate.now(), transactionDTO.getAmount(), transactionDTO.getReason());
        if (currentUser.getBalance() - (transactionDTO.getAmount() + (transactionDTO.getAmount() * COMMISSION.TRANSACTION_COMMISSION)) >= 0)
            currentUser.setBalance(currentUser.getBalance() - (transactionDTO.getAmount() + (transactionDTO.getAmount() * 0.005)));
        else
            log.error("Insufficient balance");
        friend.setBalance(friend.getBalance() + transactionDTO.getAmount());

        transactionRepository.save(transaction);
    }


}
