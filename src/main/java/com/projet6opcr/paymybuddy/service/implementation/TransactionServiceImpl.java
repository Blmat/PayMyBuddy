package com.projet6opcr.paymybuddy.service.implementation;

import com.projet6opcr.paymybuddy.constant.Commission;
import com.projet6opcr.paymybuddy.model.dto.TransactionDTO;
import com.projet6opcr.paymybuddy.exception.UserNotFoundException;
import com.projet6opcr.paymybuddy.model.Transaction;
import com.projet6opcr.paymybuddy.repository.TransactionRepository;
import com.projet6opcr.paymybuddy.repository.UserRepository;
import com.projet6opcr.paymybuddy.service.PrincipalUser;
import com.projet6opcr.paymybuddy.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final PrincipalUser principalUser;

    @Override
    public void sendMoney(String friendEmail, TransactionDTO transactionDTO) {

        var debtor = principalUser.getCurrentUserOrThrowException();

        var creditor = userRepository.findByEmail(friendEmail)
                .orElseThrow(() -> new UserNotFoundException("User not found with email = " + friendEmail));

        var transaction = new Transaction(debtor, creditor, transactionDTO,  Commission.TRANSACTION_COMMISSION);

        debtor.debitBalanceAmount(transaction);
        creditor.creditBalanceAmount(transaction);

        log.info("successful transfer : " + transaction);

        transactionRepository.save(transaction);
        userRepository.saveAll(List.of(debtor, creditor));
    }
}
