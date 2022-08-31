package com.projet6opcr.paymybuddy.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Transaction extends CrudRepository<Transaction,Integer> {
}
