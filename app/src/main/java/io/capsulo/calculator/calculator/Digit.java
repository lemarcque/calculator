package io.capsulo.calculator.calculator;

/**
 * @author lemarcque
 * Description Classe représentant un nombre PAS CHIFFRE
 */

public class Digit {


    public static String replaceSign(String digit) {
        if(digit.length() > 0) {
            if(digit.charAt(0) == 'A')
                return digit.replace('A', '-');
        }

        return digit;
    }

    public static String replaceSignA(String digit) {
        if(digit.length() > 0) {
            if(digit.charAt(0) == '-')
                return digit.replace('-', 'A');
        }

        return digit;
    }

}
