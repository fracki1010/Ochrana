package com.francogaldame.ochranaBank.repositories;

import com.francogaldame.ochranaBank.models.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<Loan, Long> {
    boolean existsById(Long id);
    Loan findByName(String name);
}
