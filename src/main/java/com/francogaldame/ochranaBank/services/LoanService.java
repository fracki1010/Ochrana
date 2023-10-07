package com.francogaldame.ochranaBank.services;

import com.francogaldame.ochranaBank.dtos.LoanApplicationDTO;
import com.francogaldame.ochranaBank.dtos.LoanDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.Set;


public interface LoanService {

    Set<LoanDTO> getLoans();

    ResponseEntity applyForLoan(LoanApplicationDTO loanApplicationDTO, Authentication authentication);

}
