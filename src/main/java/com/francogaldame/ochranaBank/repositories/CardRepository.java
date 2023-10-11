package com.francogaldame.ochranaBank.repositories;

import com.francogaldame.ochranaBank.models.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Long> {
    Boolean existsByNumber(String number);
    Boolean existsByCvv(String cvv);
    Card findByNumber(String number);
}
