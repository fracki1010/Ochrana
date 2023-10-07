package com.francogaldame.ochranaBank.repositories;

import com.francogaldame.ochranaBank.models.Card;
import com.francogaldame.ochranaBank.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface CardRepository extends JpaRepository<Card, Long> {
    Boolean existsByNumber(String number);
    Boolean existsByCvv(String cvv);
    Client findByClient(String client);
}
