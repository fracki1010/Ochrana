package com.francogaldame.ochranaBank.dtos;

import com.francogaldame.ochranaBank.models.Pending;

public class PendingDTO {
    private Long id;
    private String name;
    private String emailClient;
    private String typeCard;
    private String colorCard;
    private Long Loan;
    private Double amountLoan;
    private int payments;

    public PendingDTO(Pending pending){
        this.id = pending.getId();
        this.name = pending.getName();
        this.emailClient = pending.getEmailClient();
        this.typeCard = pending.getTypeCard();
        this.colorCard = pending.getColorCard();
        this.Loan = pending.getLoan();
        this.amountLoan = pending.getAmountLoan();
        this.payments = pending.getPayments();
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
