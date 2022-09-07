package com.projet6opcr.paymybuddy.repository;

import com.projet6opcr.paymybuddy.model.Bank;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankRepository extends CrudRepository<Bank,Long> {

}
