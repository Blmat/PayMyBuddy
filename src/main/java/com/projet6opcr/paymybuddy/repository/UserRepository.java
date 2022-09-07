package com.projet6opcr.paymybuddy.repository;

import com.projet6opcr.paymybuddy.dto.UserDTO;
import com.projet6opcr.paymybuddy.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User,Long> {

//    User saveUser(UserDTO userDTO);
//    User findByEmail(String email);
//
//    boolean existsByEmail(String email);

//    List<User> findAll();
}
