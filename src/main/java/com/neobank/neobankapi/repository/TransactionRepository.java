package com.neobank.neobankapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.neobank.neobankapi.entities.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long>{

}
