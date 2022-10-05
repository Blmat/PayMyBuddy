package com.projet6opcr.paymybuddy.repository;

import com.projet6opcr.paymybuddy.dto.UserDTO;
import com.projet6opcr.paymybuddy.model.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserAccount,Integer> {

    Optional<UserAccount> findByEmail(String email);

    User findById(int id);
}
