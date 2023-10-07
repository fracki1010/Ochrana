package com.francogaldame.ochranaBank.controllers;


import com.francogaldame.ochranaBank.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@Transactional
@RestController
@RequestMapping("/api")
public class TransactionController {

@Autowired
private TransactionService transactionService;


    @PostMapping("/transactions")
    public ResponseEntity<Object> createdTransaction(
            @RequestParam String fromAccountNumber, @RequestParam String toAccountNumber,
            @RequestParam Double amount, @RequestParam String description,
            Authentication authentication){
        return transactionService.createdTransaction(fromAccountNumber, toAccountNumber, amount,
                description, authentication);
    }

}
