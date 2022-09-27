package com.projet6opcr.paymybuddy.repository;

import com.projet6opcr.paymybuddy.model.Relation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RelationRepository  extends JpaRepository<Relation, Integer> {
}
