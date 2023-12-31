package com.francogaldame.ochranaBank.dtos;


import com.francogaldame.ochranaBank.models.Account;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class AccountDTO {
    private Long id;

    private String number;
    private LocalDate creationDate;
    private Double balance;
    private Set<TransactionDTO> transactions = new HashSet<>();

    public AccountDTO(Account account) {
        this.id = account.getId();
        this.number = account.getNumber();
        this.creationDate = account.getCreationDate();
        this.balance = account.getBalance();
        this.transactions = account
                            .getTransactions()
                            .stream()
                            .map(transaction -> new TransactionDTO(transaction))
                            .collect(Collectors.toSet());
    }

    public Long getId() {
        return id;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public String getNumber() {
        return number;
    }

    public LocalDate getDate() {
        return creationDate;
    }

    public Double getBalance() {
        return balance;
    }

    public Set<TransactionDTO> getTransactions() {
        return transactions;
    }
}
