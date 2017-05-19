package io.capsulo.calculator.calculator.operation.base;

import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import io.capsulo.calculator.calculator.Constants;
import io.capsulo.calculator.calculator.Digit;

/**
 * @author lemarcque
 */

public class ComplexBlock extends BaseOperation {


    public ComplexBlock() {

    }

    public ComplexBlock(double firstValue, double secondeValue) {
        this.firstValue = firstValue;
        this.secondeValue = secondeValue;
    }

    public void calculValues(String formula, HashMap<String, String> operator) {
        int minPositionValue = 0;
        int maxPositionValue = formula.length();
        int position = 0;
        int index = 0;
        boolean verification = false;
        boolean isValueNull = false;

        String strLeftValue;
        String strRightValue;
        ArrayList<String> currentLeftNumber = new ArrayList<String>();
        ArrayList<String> currentRightNumber = new ArrayList<String>();

        for(int sens = 0; sens < 2; sens++) {

            while(!isValueNull) {

                index += (sens == 0) ? -1 : 1;
                position = Integer.parseInt(operator.get("pos")) + index;
                if(sens == 0) verification = (position >= minPositionValue);
                if(sens == 1) verification = position < maxPositionValue;

                if(verification) {
                    String values = String.valueOf(formula.charAt(position));

                    if(!values.equals("x") && !values.equals("÷") && !values.equals("+") && !values.equals("-")) {
                        if(sens == 0) currentLeftNumber.add(values);
                        if(sens == 1) currentRightNumber.add(values);
                        //continue;   // should work normally
                    }else {
                        isValueNull = true;
                        index = 0;
                    }
                }else {
                    index = 0;
                    isValueNull = true;
                }
            }

            index = 0;
            isValueNull = false;
        }


        Collections.reverse(currentLeftNumber);
        Collections.reverse(currentRightNumber);
        strLeftValue = Digit.replaceSign(TextUtils.join("", currentLeftNumber));
        strRightValue = Digit.replaceSign(TextUtils.join("", currentRightNumber));
        if(operator.get("value").equals("x")) operator.put("value", Constants.MULTIPLE_OPERATOR);
        Log.i("a", strLeftValue);
        Log.i("b", strRightValue);
        Log.i("d", String.valueOf(Double.parseDouble(strLeftValue)));
        Log.i("d", String.valueOf(Double.parseDouble(strRightValue)));
        super.setFirstValue(Double.parseDouble(strLeftValue));
        super.setSecondeValue(Double.parseDouble(strRightValue));
        super.setOperator(operator.get("value"));
    }

    public double getLeftValue() {
        return firstValue;
    }

    public double getRightValue() {
        return secondeValue;
    }

}
