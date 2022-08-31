package com.projet6opcr.paymybuddy.repository;

import com.projet6opcr.paymybuddy.model.Bank;
import com.projet6opcr.paymybuddy.model.User;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;

@EnableJpaRepositories
public interface BankRepository extends CrudRepository<Bank,Integer> {

}
