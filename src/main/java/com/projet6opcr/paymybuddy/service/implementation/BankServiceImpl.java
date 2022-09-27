package com.projet6opcr.paymybuddy.service.implementation;

import com.projet6opcr.paymybuddy.repository.BankRepository;
import com.projet6opcr.paymybuddy.service.BankService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BankServiceImpl implements BankService {

    private final BankRepository bankRepository;


    public BankServiceImpl(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }

    @Override
    public boolean checkIfBankAccountUserExists(Integer userId) {
        return (bankRepository.findById(userId).isPresent());
    }
}
