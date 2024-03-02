package com.vishal.transactions.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vishal.transactions.domain.OperationType;

@Repository
public interface OperationTypeRepository extends JpaRepository<OperationType, Long> {
}