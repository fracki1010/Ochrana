package com.francogaldame.ochranaBank.utils;

import com.francogaldame.ochranaBank.repositories.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Random;

public class PaymentsUtils {

    @Autowired
    static LoanRepository loanRepository;

    //Calcula el interes del banco dejando 1.2 o lo que fuera
    //para luego ser multiplicado(suma) o dividido(resta)
    public static Double bankInterest(int payment, String loanName) {
        if (loanName.equals("Hipotecario")){
            switch (payment) {
                case 12:
                    return 1.2;

                case 24:
                    return 1.4;
                case 36:
                    return 1.6;
                case 48:
                    return 1.8;
                case 60:
                    return 1.9;
                default:
                    return 0.0;
            }
        } else if (loanName.equals("Personal")) {
            switch (payment){
                case 6:
                    return 1.1;
                case 12:
                    return 1.3;
                case 24:
                    return 1.6;
                default:
                    return 0.0;
            }
        } else if (loanName.equals("Automotriz")) {
            switch (payment){
                case 12:
                    return 1.3;
                case 24:
                    return 1.6;
                case 36:
                    return 1.8;
                default:
                    return 0.0;
            }
        }
        return null;
    }
}
