package com.francogaldame.ochranaBank.dtos;

public class LoanApprovedDTO {
    private String loanName;
    private Long ClientLoanId;
    private Double amount;
    private String toAccountNumber;

    public LoanApprovedDTO(String loanName, Long clientLoanId, Double amount, String toAccountNumber) {
        this.loanName = loanName;
        this.ClientLoanId = clientLoanId;
        this.amount = amount;
        this.toAccountNumber = toAccountNumber;
    }

    public String getLoanName() {
        return loanName;
    }

    public Long getClientLoanId() {
        return ClientLoanId;
    }

    public String getToAccountNumber() {
        return toAccountNumber;
    }

    public Double getAmount() {
        return amount;
    }
}
