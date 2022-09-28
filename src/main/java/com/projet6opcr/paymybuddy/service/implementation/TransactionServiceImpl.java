package com.projet6opcr.paymybuddy.service.implementation;

import com.projet6opcr.paymybuddy.constant.COMMISSION;
import com.projet6opcr.paymybuddy.dto.TransactionDTO;
import com.projet6opcr.paymybuddy.exception.InsufficientBalanceException;
import com.projet6opcr.paymybuddy.exception.UserNotFoundException;
import com.projet6opcr.paymybuddy.model.Transaction;
import com.projet6opcr.paymybuddy.model.UserAccount;
import com.projet6opcr.paymybuddy.repository.TransactionRepository;
import com.projet6opcr.paymybuddy.repository.UserRepository;
import com.projet6opcr.paymybuddy.service.PrincipalUser;
import com.projet6opcr.paymybuddy.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final PrincipalUser principalUser;

//    @Override
//    public void sendMoney(TransactionDTO transactionDTO) {
//
//        UserAccount currentUser = principalUser.getCurrentUserOrThrowException();
//        var friend = userRepository.findByEmail(String.valueOf(transactionDTO.getReceiverId()))
//                .orElseThrow(() -> new UserNotFoundException("User not found with email = "+transactionDTO.getReceiverId()));
//        Transaction transaction = new Transaction(currentUser.getId(), friend.getId(),
//                LocalDate.now(), transactionDTO.getAmount(), transactionDTO.getReason());
//        if (currentUser.getBalance() - (transactionDTO.getAmount() + (transactionDTO.getAmount() * COMMISSION.TRANSACTION_COMMISSION)) >= 0)
//            currentUser.setBalance(currentUser.getBalance() - (transactionDTO.getAmount() + (transactionDTO.getAmount() * 0.005)));
//        else
//            log.error("Insufficient balance");
//        friend.setBalance(friend.getBalance() + transactionDTO.getAmount());
//
//        transactionRepository.save(transaction);
//    }

//    @Override
//    @Transactional
//    public void sendMoney(TransactionDTO transactionDTO) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String username = authentication.getName();
//        Optional<UserAccount> currentUser = userRepository.findByEmail(username);
//
//        Optional<UserAccount> friend = userRepository.findByEmail(String.valueOf(transactionDTO.getReceiverId()));
//        Transaction transaction = new Transaction(currentUser.get().getId(), friend.get().getId(),
//                LocalDate.now(), transactionDTO.getAmount(), transactionDTO.getReason());
//        if (currentUser.get().getBalance() - (transactionDTO.getAmount() + (transactionDTO.getAmount() * COMMISSION.TRANSACTION_COMMISSION)) >= 0)
//            currentUser.get().setBalance(currentUser.get().getBalance() - (transactionDTO.getAmount() + (transactionDTO.getAmount() * 0.005)));
//        else
//            log.error("Insufficient balance");
//        friend.get().setBalance(friend.get().getBalance() + transactionDTO.getAmount());
//
//        transactionRepository.save(transaction);
//    }

    @Override
    public void sendMoney(String friendEmail,TransactionDTO transactionDTO) {

        UserAccount currentUser = principalUser.getCurrentUserOrThrowException();
        var friend = userRepository.findByEmail(friendEmail)
                .orElseThrow(() -> new UserNotFoundException("User not found with email = "+transactionDTO.getReceiverId()));
        Transaction transaction = new Transaction(currentUser.getId(), friend.getId(),
                LocalDate.now(), transactionDTO.getAmount(), transactionDTO.getReason());
        if (currentUser.getBalance() - (transactionDTO.getAmount() + (transactionDTO.getAmount() * COMMISSION.TRANSACTION_COMMISSION)) >= 0)
            currentUser.setBalance(currentUser.getBalance() - (transactionDTO.getAmount() + (transactionDTO.getAmount() * 0.005)));
        else
           throw new InsufficientBalanceException("sorry you don't have enough money ");

        friend.setBalance(friend.getBalance() + transactionDTO.getAmount());
        log.info("successful transfer");
        transactionRepository.save(transaction);
    }
}
