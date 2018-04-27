package io.capsulo.calculator.calculator;

import android.text.TextUtils;

import java.util.ArrayList;

import io.capsulo.calculator.calculator.operation.ComplexOperation;
import io.capsulo.calculator.calculator.operation.DivideOperation;

/**
 * @author lemarcque
 * Description : Classe représentant la machine à calculer et effectuant des opérations mathématiques
 */

public class Calculator {

    private String formula;
    private String result;                                // output value : computetext area
    private String lastOperation;                         // output value : resulttext area
    private ArrayList<String> currentComputation;         // variable for computing number
    private ArrayList<String> currrentWritingNumber;      // variable for current number writed
    private String MODE;

    public Calculator() {
        currentComputation = new ArrayList<>();
        currrentWritingNumber = new ArrayList<>();
        result = "";
        formula = "";
        lastOperation = "";
        MODE = Mode.RESET;
    }


    /* we add the result of previous operaion, if it exists */
    private void addLastOperation() {
        if(!lastOperation.equals("")) {
            for(int l = 0; l < lastOperation.length(); l++) {
                currrentWritingNumber.add(String.valueOf(lastOperation.charAt(l)));
            }

            MODE = Mode.WRITING;
            formula = "";
        }
    }

    public void addOperator(String operator) {
        // we can add an operator only if at least one number have been written : "0" => "0 +"
        if(currrentWritingNumber.size()  > 0) {
            this.addLastOperation();

            // update
            /*if(this.getSign(Utils.StringToDouble(Utils.arrayToString(currrentWritingNumber))).equals(Constants.MINUS))
                currrentWritingNumber.set(0, "A");*/


            formula += result;
            formula += operator;

            currrentWritingNumber.clear();
            result = "";

            this.addValues(operator);
        }
    }

    public void addNumber(String digit) {
        // en cas de reset : 0
        if(MODE.equals(Mode.RESET)) {
            result = "";
            MODE = Mode.WRITING;
        }else if (MODE.equals(Mode.OFF)) {
            reset();
            result = "";
            MODE = Mode.WRITING;
        }

        currrentWritingNumber.add(digit);
        result += digit;
        this.addValues(digit);
    }

    /* add value to the actual written calcul (root, integer, point) */
    private void addValues(String v) {
        currentComputation.add(v);
    }

    public void compute() {
        if(MODE.equals(Mode.WRITING)) {
            MODE = Mode.OFF;

            // we try to calcul only if there is something written, exemple : "[3,+,3]"
            if(currentComputation.size() > 0) {
                ComplexOperation complexOperation = new ComplexOperation(currentComputation);
                formula += result;
                result = String.valueOf(complexOperation.getResult());
                result = TextUtils.join("", currentComputation);
            }
            else if(currrentWritingNumber.size() > 0) {
                // if user doesnt write any formula, we show this number
                result = TextUtils.join("", currrentWritingNumber);
            }
        }

        lastOperation = result;
    }


    public void reset() {
        currentComputation.clear();
        currrentWritingNumber.clear();
        result = "0";
        formula = "";
        lastOperation = "";
        MODE = Mode.RESET;
    }

    public void setSign() {
        double digit = Utils.StringToDouble(Utils.arrayToString(currrentWritingNumber));
        String sign = this.getSign(digit);

        if(sign.equals(Constants.PLUS)) {
            currrentWritingNumber.add(0, "-");
        }else if(sign.equals(Constants.MINUS)) {
            currrentWritingNumber.remove(0);
        }

        result = Utils.arrayToString(currrentWritingNumber);
    }

    private String getSign(Double digit) {
        String strDigit = String.valueOf(digit);
        if(strDigit.charAt(0) == '-')
            return Constants.MINUS;
        else if(strDigit.charAt(0) == '+')
            return Constants.PLUS;

        return Constants.PLUS;
    }

    /* use float instead.. */
    public void getPercent() {
        if(currrentWritingNumber.size() > 0) {
            Double n = Double.parseDouble(TextUtils.join("", currrentWritingNumber));
            n = new DivideOperation(n, 100).getResult();

            String writerNumber = String.valueOf(n);
            currrentWritingNumber.clear();

            for(int i = 0; i < writerNumber.length(); i++) {
                currrentWritingNumber.add(String.valueOf(writerNumber.charAt(i)));
            }

            result = Utils.arrayToString(currrentWritingNumber);
            formula = "";
        }
    }

    /* GETTER */
    public String getFormula() {
        return Digit.replaceSign(Utils.parseSpace(formula));
    }

    public String getResult() {
        return Digit.replaceSign(result);
    }
}
