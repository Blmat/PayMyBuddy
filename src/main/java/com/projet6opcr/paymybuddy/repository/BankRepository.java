package com.projet6opcr.paymybuddy.repository;

import com.projet6opcr.paymybuddy.model.BankAccount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankRepository extends CrudRepository<BankAccount, Integer> {

}
