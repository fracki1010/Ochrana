package com.francogaldame.ochranaBank.services;


import com.francogaldame.ochranaBank.dtos.LoanApplicationDTO;
import com.francogaldame.ochranaBank.dtos.LoanApprovedDTO;
import com.francogaldame.ochranaBank.dtos.LoanDTO;
import com.francogaldame.ochranaBank.dtos.LoanDeleteDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.Set;


public interface LoanService {

    Set<LoanDTO> getLoans();

    ResponseEntity applyForLoan(LoanApplicationDTO loanApplicationDTO, Authentication authentication);

    ResponseEntity approvedLoan(LoanApprovedDTO loanApprovedDTO);

    ResponseEntity deleteLoan(Long loanDeleteDTO);
}
