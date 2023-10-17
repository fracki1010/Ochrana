package com.francogaldame.ochranaBank.controllers;


import com.francogaldame.ochranaBank.dtos.CardDTO;
import com.francogaldame.ochranaBank.models.CardColor;
import com.francogaldame.ochranaBank.models.CardType;
import com.francogaldame.ochranaBank.models.Client;
import com.francogaldame.ochranaBank.services.CardService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CardController {

    @Autowired
    private CardService cardService;


    @GetMapping("/cards")
    public List<CardDTO> getAllCards() {
        return cardService.getCards();
    }
    @PostMapping("/clients/current/cards")
    public ResponseEntity<Object> createdCard(
            @RequestParam CardType cardType, @RequestParam CardColor cardColor,
            Authentication authentication){
        return cardService.createdCard(cardType, cardColor, authentication);
    }

    @PostMapping("/clients/current/cards/approved")
    public ResponseEntity<Object> approvedCard(@RequestParam String numberCard){
        return cardService.approvedCard(numberCard);
    }

    @PostMapping("/clients/current/cards/delete")
    public ResponseEntity<Object> deleteCard(@RequestParam String numberCard){
        return cardService.deleteCard(numberCard);
    }

}