package com.francogaldame.ochranaBank.dtos;

import com.francogaldame.ochranaBank.models.ClientLoan;
import com.francogaldame.ochranaBank.models.Loan;
import com.francogaldame.ochranaBank.utils.PaymentsUtils;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


public class ClientLoanDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name="native", strategy = "native")
    private Long id;
    private String name;
    private int payments;
    private Double amount;
    private String toAccountTransfer;
    private Boolean approved;
    private Loan loan_id;

    public ClientLoanDTO(ClientLoan clientLoan) {
        this.id = clientLoan.getId();
        this.name = clientLoan.getLoan().getName();
        this.payments = clientLoan.getPayments();
        this.amount = clientLoan.getAmount() / PaymentsUtils.bankInterest(clientLoan.getPayments(), clientLoan.getLoan().getName());
        this.toAccountTransfer = clientLoan.getToAccountTransfer();
        this.approved = clientLoan.getApproved();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPayments() {
        return payments;
    }

    public Double getAmount() {
        return amount;
    }

    public String getToAccountTransfer() {
        return toAccountTransfer;
    }

    public Boolean getApproved() {
        return approved;
    }
}
