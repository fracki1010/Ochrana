package com.francogaldame.ochranaBank.services;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;


public interface TransactionService {
    ResponseEntity<Object> createdTransaction(
             String fromAccountNumber,  String toAccountNumber, Double amount,  String description,
            Authentication authentication);
}
