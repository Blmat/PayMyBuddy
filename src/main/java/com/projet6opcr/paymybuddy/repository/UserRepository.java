package com.projet6opcr.paymybuddy.repository;

import com.projet6opcr.paymybuddy.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User,Integer> {
    User findByEmail(String email);

    User findById(int id);
}
