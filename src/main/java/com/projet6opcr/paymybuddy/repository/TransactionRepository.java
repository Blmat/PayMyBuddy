package com.projet6opcr.paymybuddy.repository;

import com.projet6opcr.paymybuddy.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    List<Transaction> findAllByDebtorUserIdOrderByDateDesc(Integer debtorId);

}
