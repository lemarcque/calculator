package io.capsulo.calculator.calculator;

import android.text.TextUtils;

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

    public static String arrayToString(ArrayList<String> arr){
        return TextUtils.join("", arr);
    }
}
