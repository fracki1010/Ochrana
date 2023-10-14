package com.francogaldame.ochranaBank.dtos;

public class LoanDeleteDTO {
    private Long loanDeleteId;

    public LoanDeleteDTO(Long loanDeleteId) {
        this.loanDeleteId = loanDeleteId;
    }

    public Long getLoanDeleteId() {
        return loanDeleteId;
    }
}
