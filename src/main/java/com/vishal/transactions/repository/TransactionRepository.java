package com.vishal.transactions.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vishal.transactions.domain.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}