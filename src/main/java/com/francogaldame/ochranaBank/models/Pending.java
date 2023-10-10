package com.francogaldame.ochranaBank.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Pending {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name="native", strategy = "native")
    private Long id;
    private String name;
    private String emailClient;
    private String typeCard;
    private String colorCard;
    private Long Loan;
    private Double amountLoan;
    private int payments;

    public Pending() {
    }

    public Pending(String name, String emailClient, String typeCard,
                   String cardNumber, Long loan, Double amountLoan, int payments) {
        this.name = name;
        this.emailClient = emailClient;
        this.typeCard = typeCard;
        this.colorCard = colorCard;
        Loan = loan;
        this.amountLoan = amountLoan;
        this.payments = payments;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmailClient() {
        return emailClient;
    }

    public String getTypeCard() {
        return typeCard;
    }

    public String getColorCard() {
        return colorCard;
    }

    public Long getLoan() {
        return Loan;
    }

    public Double getAmountLoan() {
        return amountLoan;
    }

    public int getPayments() {
        return payments;
    }
}

