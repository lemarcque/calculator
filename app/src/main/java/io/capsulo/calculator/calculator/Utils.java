package io.capsulo.calculator.calculator;

import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;

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

    public static String doubleToString(double d) {
        Double digit = d;
        Integer integer = (int) d;
        digit = (double) integer;
        digit = digit - digit;
        if(digit == 0.0) {
            return String.valueOf((double) new Double(d).intValue());
        }
        return String.valueOf(d);
    }

    public static String arrayToString(ArrayList<String> arr){
        return TextUtils.join("", arr);
    }
}
