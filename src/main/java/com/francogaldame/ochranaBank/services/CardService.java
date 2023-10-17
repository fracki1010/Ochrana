package com.francogaldame.ochranaBank.services;

import com.francogaldame.ochranaBank.dtos.CardDTO;
import com.francogaldame.ochranaBank.models.CardColor;
import com.francogaldame.ochranaBank.models.CardType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface CardService {

    List<CardDTO> getCards();
    ResponseEntity<Object> createdCard(CardType cardType, CardColor cardColor,
                                       Authentication authentication);

    ResponseEntity<Object> approvedCard(String numberCard);

    ResponseEntity<Object> deleteCard(String numberCard);
}