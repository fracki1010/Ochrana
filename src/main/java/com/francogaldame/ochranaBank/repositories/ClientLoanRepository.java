package com.francogaldame.ochranaBank.repositories;

import com.francogaldame.ochranaBank.models.ClientLoan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientLoanRepository extends JpaRepository<ClientLoan, Long> {
}
