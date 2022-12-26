package com.projet6opcr.paymybuddy.repository;

import com.projet6opcr.paymybuddy.model.Transaction;
import com.projet6opcr.paymybuddy.model.dto.TransactionDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    List<Transaction> findAllByDebtor_UserIdOrderByDateDesc(Integer debtorId);

}
