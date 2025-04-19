package com.neobank.neobankapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.neobank.neobankapi.entities.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long>{

    List<Transaction> findBySender_ClientId(String senderId);

}
