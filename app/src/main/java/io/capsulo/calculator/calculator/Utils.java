package io.capsulo.calculator.calculator;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author lemarcque
 * Description fonction utilitaires
 */

public class Utils {

    public static String intToDouble(double v) {
        return  String.valueOf(v);
    }

    public static double StringToDouble(String str) {
        if(str.length() > 0)
            return Double.parseDouble(str);

        return 0;
    }

    // Handling floating number
    public static String doubleToString(double d) {
        Double digit = d;
        Integer integer = (int) d;
        digit = (double) integer;
        digit = d - digit;

        if(digit == 0.0)
            return String.valueOf(new Double(d).intValue());

        return String.valueOf(d);
    }

    public static double removeDecimal(double d) {
        Double digit = d;
        Integer integer = (int) d;
        digit = (double) integer;
        digit = digit - digit;
        if(digit == 0.0)
            return Double.valueOf(d).intValue();
        return d;
    }

    public static String arrayToString(ArrayList<String> arr){
        return TextUtils.join("", arr);
    }

    public static String parseSpace(String formula) {
        String str = new String();
        for(char c : formula.toCharArray()) {
            if(c == Constants.DIVIDE_OPERATOR.charAt(0) || c == Constants.MULTIPLE_OPERATOR.charAt(0) || c == Constants.PLUS_OPERATOR.charAt(0) || c == Constants.MINUS_OPERATOR.charAt(0))
                str += " " + String.valueOf(c) + " ";
            else
                str += String.valueOf(c);
        }
        return str;
    }
}
