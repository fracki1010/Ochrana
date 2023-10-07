package com.francogaldame.ochranaBank.repositories;

import com.francogaldame.ochranaBank.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
