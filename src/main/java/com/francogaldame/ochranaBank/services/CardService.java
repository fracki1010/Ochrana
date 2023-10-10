package com.francogaldame.ochranaBank.services;

import com.francogaldame.ochranaBank.models.CardColor;
import com.francogaldame.ochranaBank.models.CardType;
import com.francogaldame.ochranaBank.models.Client;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

public interface CardService {


    ResponseEntity<Object> createdCard(CardType cardType, CardColor cardColor,
                                       String emailClientLoan);

}