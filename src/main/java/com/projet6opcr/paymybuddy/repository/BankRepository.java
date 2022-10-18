package com.projet6opcr.paymybuddy.repository;

import com.projet6opcr.paymybuddy.model.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankRepository extends JpaRepository<BankAccount, Integer> {

}
