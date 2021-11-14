package com.conversor.currency.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.conversor.currency.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long>{

}
