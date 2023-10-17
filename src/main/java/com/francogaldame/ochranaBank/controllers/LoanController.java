package com.francogaldame.ochranaBank.controllers;


import com.francogaldame.ochranaBank.dtos.*;
import com.francogaldame.ochranaBank.services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.Set;

@Transactional
@RestController
@RequestMapping("/api")
public class LoanController {

    @Autowired
    LoanService loanService;


    @GetMapping("/client-loans")
    public Set<ClientLoanDTO> getClientLoans(){ return loanService.getClientLoans();}

    @GetMapping("/loans")
    public Set<LoanDTO> getLoans(){
        return loanService.getLoans();
    }

    @PostMapping("/loans")
    public ResponseEntity applyForLoan(@RequestBody LoanApplicationDTO loanApplicationDTO,
                                       Authentication authentication) {
    return loanService.applyForLoan(loanApplicationDTO, authentication);
    }

    @PostMapping("/loans/approved")
    public ResponseEntity approvedLoan(@RequestBody LoanApprovedDTO loanApprovedDTO) {
    return loanService.approvedLoan(loanApprovedDTO);
    }

    @PostMapping("/loans/delete")
    public ResponseEntity<Object> deleteLoan(@RequestParam Long loanDeleteId) {
    return loanService.deleteLoan(loanDeleteId);
    }

}
