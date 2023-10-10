package com.francogaldame.ochranaBank.services.implement;

import com.francogaldame.ochranaBank.models.Card;
import com.francogaldame.ochranaBank.models.CardColor;
import com.francogaldame.ochranaBank.models.CardType;
import com.francogaldame.ochranaBank.models.Client;
import com.francogaldame.ochranaBank.repositories.CardRepository;
import com.francogaldame.ochranaBank.repositories.ClientRepository;
import com.francogaldame.ochranaBank.services.CardService;
import com.francogaldame.ochranaBank.utils.CardUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CardServiceImplement implements CardService {
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private ClientRepository clientRepository;

    @Override
    public ResponseEntity<Object> createdCard(CardType cardType,CardColor cardColor,
            String emailClientLoan) {

        //Variables para el color y tipo
        String colorOrTypeCard = "";
        Client client = clientRepository.findByEmail(emailClientLoan);

        //Tarjetas del cliente del tipo pedido
        Set<Card> currentCardType = client
                .getCards()
                .stream()
                .filter(card -> card.getType() == cardType)
                .collect(Collectors.toSet());

        //Tarjetas de color pedido dentro del tipo pedido
        Set<Card> currentCardColor = currentCardType
                .stream()
                .filter(card -> card.getColor() == cardColor)
                .collect(Collectors.toSet());


        //Tarjetas de diferente tipo repetidas
        if (currentCardType.size() == 3) {
            switch (cardType) {
                case CREDIT:
                    colorOrTypeCard = "CREDIT";
                    break;
                case DEBIT:
                    colorOrTypeCard = "DEBIT";
                    break;
            }
            return new ResponseEntity<>("Alcanzo el limite de tarjetas de " + colorOrTypeCard, HttpStatus.FORBIDDEN);
        }


        //Tarjetas del mismo color repetidas
        if (currentCardColor.size() == 1) {
            switch (cardColor) {
                case GOLD:
                    colorOrTypeCard = "GOLD";
                    break;
                case SILVER:
                    colorOrTypeCard = "SILVER";
                    break;
                case TITANIUM:
                    colorOrTypeCard = "TITANIUM";
                    break;
            }
            return new ResponseEntity<>("Alcanzo el limite de la tarjeta " + colorOrTypeCard, HttpStatus.FORBIDDEN);
        }


        //Creacion de cvv aleatorio
        String cvv;
        do {
            cvv = CardUtils.getCvv();
        } while (cardRepository.existsByCvv(cvv));


        //Creacion del número de tarjera aleatorio
        String numberCard;
        do {

            numberCard = CardUtils.creationNumberCard();

        } while (cardRepository.existsByNumber(numberCard));


        //Creacion de tarjeta
        Card card = new Card(client.getFirstName() + " " + client.getLastName(),
                cardType,
                cardColor,
                numberCard,
                cvv,
                LocalDate.now(),
                LocalDate.now().plusYears(5));

        //Asignacion de tarjeta a cliente
        clientRepository.findByEmail(client.getEmail()).addCard(card);

        //Guardado de tarjera
        cardRepository.save(card);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    private static String getCvv() {
        return String.valueOf(CardUtils.randomNumber(100, 999));
    }
}
