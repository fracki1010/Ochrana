package com.francogaldame.ochranaBank.dtos;


import com.francogaldame.ochranaBank.models.Account;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class AccountDTO {
    private Long id;
    private String owner;
    private String number;
    private LocalDate creationDate;
    private Double balance;
    private Boolean approved;
    private Set<TransactionDTO> transactions = new HashSet<>();

    public AccountDTO(Account account) {
        this.id = account.getId();
        this.number = account.getNumber();
        this.creationDate = account.getCreationDate();
        this.balance = account.getBalance();
        this.approved = account.getApproved();
        this.owner = account.getClient().getFirstName() +
                " "+ account.getClient().getLastName();
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

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
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

    public Boolean getApproved() {
        return approved;
    }

    public Set<TransactionDTO> getTransactions() {
        return transactions;
    }
}
