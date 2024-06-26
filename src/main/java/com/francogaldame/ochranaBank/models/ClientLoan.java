package com.francogaldame.ochranaBank.models;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
public class ClientLoan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name="native", strategy = "native")
    private Long id;
    private int payments;
    private double amount;
    private String toAccountTransfer;
    private Boolean approved;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private Client client;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "loan_id")
    private Loan loan;
    public ClientLoan() {
    }

    public ClientLoan(int payments, Double amount, String toAccountTransfer, Client client, Loan loan, Boolean approved) {
        this.payments = payments;
        this.amount = amount;
        this.toAccountTransfer = toAccountTransfer;
        this.approved = approved;
        this.client = client;
        this.loan = loan;
    }

    public Long getId() {
        return id;
    }

    public int getPayments() {
        return payments;
    }

    public void setPayment(int payments) {
        this.payments = payments;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Loan getLoan() {
        return loan;
    }

    public void setLoan(Loan loan) {
        this.loan = loan;
    }

    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public String getToAccountTransfer() {
        return toAccountTransfer;
    }
}
