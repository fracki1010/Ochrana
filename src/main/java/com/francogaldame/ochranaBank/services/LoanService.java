package com.francogaldame.ochranaBank.services;


import com.francogaldame.ochranaBank.dtos.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.Set;


public interface LoanService {

    Set<LoanDTO> getLoans();

    Set<ClientLoanDTO> getClientLoans();

    ResponseEntity applyForLoan(LoanApplicationDTO loanApplicationDTO, Authentication authentication);

    ResponseEntity approvedLoan(LoanApprovedDTO loanApprovedDTO);

    ResponseEntity deleteLoan(Long loanDeleteDTO);

}
