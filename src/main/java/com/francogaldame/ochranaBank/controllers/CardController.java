package com.francogaldame.ochranaBank.controllers;


import com.francogaldame.ochranaBank.models.CardColor;
import com.francogaldame.ochranaBank.models.CardType;
import com.francogaldame.ochranaBank.models.Client;
import com.francogaldame.ochranaBank.services.CardService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CardController {

    @Autowired
    private CardService cardService;


    @PostMapping("/clients/current/cards")
    public ResponseEntity<Object> createdCard(
            @RequestParam CardType cardType, @RequestParam CardColor cardColor,
            @RequestParam String emailClientLoad){
        return cardService.createdCard(cardType, cardColor, emailClientLoad);
    }

}