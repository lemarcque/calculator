package io.capsulo.calculator.calculator;

import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import io.capsulo.calculator.calculator.operation.ComplexOperation;
import io.capsulo.calculator.calculator.operation.DivideOperation;
import io.capsulo.calculator.calculator.operation.MinusOperation;
import io.capsulo.calculator.calculator.operation.MultiplyOperation;
import io.capsulo.calculator.calculator.operation.PlusOperation;

/**
 * @author lemarcque
 * Description : Classe permettant d'effectuer des opérations mathématiques
 */

public class Calculator {

    private String formula;
    private String result;
    private String lastOperation;
    private ArrayList<String> currentComputation;         // computetext area
    private ArrayList<String> currrentWritingNumber;      // resulttext area
    private boolean resetComputation;

    public Calculator() {
        currentComputation = new ArrayList<String>();
        currrentWritingNumber = new ArrayList<String>();
        result = "";
        formula = "";
    }

    /* Adding values (root, integer, point)  */
    public void addValues(String values) {
        if(resetComputation) {
            resetComputation = false;
            reset();

            if(lastOperation.length() > 0) {
                for(int l = 0; l < lastOperation.length(); l++) {
                    currrentWritingNumber.add(String.valueOf(lastOperation.charAt(l)));
                }
            }
        }

        currrentWritingNumber.add(values);
        result += values;
    }

    /* On ajoute le nombre actuellement entrain d'être écrit au calcul
       et on rénitialise sa valeur à zéro.
     */
    public void updateCompute(String operator) {
        // Uniquement si un nombre est écrit
        if(currrentWritingNumber.size() > 0) {
            if(this.getSign(Utils.StringToDouble(Utils.arrayToString(currrentWritingNumber))).equals(Constants.MINUS))
                currrentWritingNumber.set(0, "A");

            for(String n :  currrentWritingNumber) {
                currentComputation.add(n);
            }

            currrentWritingNumber.clear();
            result = "";
            if(operator.length() > 0) currentComputation.add(operator);
            formula = Utils.arrayToString(currentComputation);
        }
    }

    /* Calculate formula */
    public void compute() {
        if(currentComputation.size() > 0) {
            updateCompute("");

            ComplexOperation complexOperation = new ComplexOperation(currentComputation);
            double complexOperationResult = complexOperation.getResult();
            Log.i("OK_VIA_COMPLEXOPERATION", String.valueOf(complexOperationResult));

            result = String.valueOf(complexOperation);
            result = TextUtils.join("", currentComputation);
            currentComputation.clear();
        }
        else if(currrentWritingNumber.size() > 0) {
            // if user doesnt write any formula, we show this number
            result = TextUtils.join("", currrentWritingNumber);
            reset();
        }

        resetComputation = true;
        lastOperation = result;
    }


    public void reset() {
        currentComputation.clear();
        currrentWritingNumber.clear();
        result = "";
        formula = "";
        lastOperation = "";
    }

    public void setSign() {
        double digit = Utils.StringToDouble(Utils.arrayToString(currrentWritingNumber));
        String sign = this.getSign(digit);
        if(sign.equals(Constants.PLUS)) {
            currrentWritingNumber.add(0, "-");  // B -> minus
        }else if(sign.equals(Constants.MINUS)) {
            currrentWritingNumber.remove(0);
        }

        result = Utils.arrayToString(currrentWritingNumber);
    }

    private String getSign(Double digit) {
        String strDigit = String.valueOf(digit);

        if(strDigit.charAt(0) == '-') {
            return Constants.MINUS;
        }else if(strDigit.charAt(0) == '+') {
            return Constants.PLUS;
        }

        return Constants.PLUS;
    }

    /* use float instead.. */
    public void getPercent() {
        /*if(currrentWritingNumber.size() > 0) {
            Double n = Double.parseDouble(TextUtils.join("", currrentWritingNumber));
            n = n / 100;
            String writerNumber = String.valueOf(n);
            currrentWritingNumber.clear();

            for(int i = 0; i < writerNumber.length(); i++) {
                currrentWritingNumber.add(String.valueOf(writerNumber.charAt(i)));
            }

            result = Utils.arrayToString(currrentWritingNumber);
            computation = "";
            currrentWritingNumber.clear();
        }*/
    }

    /* GETTER / SETTER */
    public String getFormula() {
        return Digit.replaceSign(formula);
    }

    public String getResult() {
        return Digit.replaceSign(result);
    }
}
