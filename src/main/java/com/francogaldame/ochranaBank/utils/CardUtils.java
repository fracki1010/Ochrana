package com.francogaldame.ochranaBank.utils;

import java.util.Random;

public class CardUtils {

    //Funcion del número aleatorio
    public static int randomNumber(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }


    //creacion del numero de tarjeta
    public static String creationNumberCard() {
        String numbersCardsComplete = "";
        for (int i = 0; i < 4; i++) {
            String numbersCard = String.valueOf(randomNumber(1000, 9999));
            numbersCardsComplete += numbersCard;
            if (i < 3) {
                numbersCardsComplete += "-";
            }
        }
        return numbersCardsComplete;
    }

    //creacion cvv
    public static String getCvv(){
        return String.valueOf(CardUtils.randomNumber(100, 999));
    }

}
