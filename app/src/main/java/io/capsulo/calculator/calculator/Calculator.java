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

            trim();

            //result = String.valueOf(complexOperation);
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

    /* trouver un opérateur (signe) dans le calcul .. */
    private void trim() {
        ArrayList<HashMap<String, String>> listOperators = new ArrayList<HashMap<String, String>>();
        boolean toReplace = false;

        for(int i = 0; i < currentComputation.size(); i++) {
            if(currentComputation.get(i).toLowerCase().equals("x") || currentComputation.get(i).equals("÷")) {
                toReplace = true;
                listOperators.add(new HashMap<String, String>());
                listOperators.get(listOperators.size() - 1).put("pos", String.valueOf(i));          // pos : position dans le tableau
                listOperators.get(listOperators.size() - 1).put("value", currentComputation.get(i));// values : valeur du nombre ou autres
            }
        }

        // Si la variable toReplace equals true, alors il faut "do stuff"
        if(toReplace)
            replace(listOperators);
        calculate();
    }

    private void replace(ArrayList<HashMap<String, String>> operators) {
        for (int i = 0; i < operators.size(); i++) {
            boolean leftValuesNotNull = true;
            boolean rightValuesNotNull = true;
            int leftNumber;
            int rightNumber;
            int index = 0;

            ArrayList<String> arrcurrentLeftNumber = new ArrayList<String>();
            String currentLeftNumber = "";
            String currentRightNumber = "";
            String newValue =  "";
            int newPosition = -1;
            int minPositionValue = 0;
            int maxPositionValue = currentComputation.size();

            while(leftValuesNotNull) {
                index--;
                int newpos = Integer.parseInt(operators.get(i).get("pos")) + index;     // Position de la valeur

                if(newpos >= minPositionValue) {
                    String values = currentComputation.get(newpos);
                    if(!values.equals("x") && !values.equals("÷") && !values.equals("+") && !values.equals("-")) {
                        arrcurrentLeftNumber.add(values);
                    }else {
                        leftValuesNotNull = false;
                        index = 0;
                    }
                }else {
                    leftValuesNotNull = false;
                    index = 0;
                }
            }

            while(rightValuesNotNull) {
                index++;
                int newpos = Integer.parseInt(operators.get(i).get("pos")) + index;     // Position de la valeur

                if(newpos < maxPositionValue) {
                    String values = currentComputation.get(newpos);
                    if(!values.equals("x") && !values.equals("÷") && !values.equals("+") && !values.equals("-")) {
                        currentRightNumber += values;
                    }else {
                        rightValuesNotNull = false;
                        index = 0;
                    }
                }else {
                    rightValuesNotNull = false;
                    index = 0;
                }
            }

            Collections.reverse(arrcurrentLeftNumber);
            currentLeftNumber = TextUtils.join("", arrcurrentLeftNumber);
            currentLeftNumber = replaceSign(currentLeftNumber);
            currentRightNumber = replaceSign(currentRightNumber);
            double leftValue = Double.parseDouble(currentLeftNumber);
            double rightValue = Double.parseDouble(currentRightNumber);
            float leftValueFloat = Float.parseFloat(currentRightNumber);
            float rightValueFloat = Float.parseFloat(currentRightNumber);

            boolean isFloating = false;

            if(currentLeftNumber.contains(".") || currentRightNumber.contains("."))
                isFloating = true;

            if(isFloating) {
                if(operators.get(i).get("value").equals("x")) newValue = Utils.doubleToString(new MultiplyOperation(leftValue, rightValue).getResult());
                if(operators.get(i).get("value").equals("÷")) newValue = Utils.doubleToString(new DivideOperation(leftValue, rightValue).getResult());
                if(operators.get(i).get("value").equals("+")) newValue = Utils.doubleToString(new PlusOperation(leftValue, rightValue).getResult());
                if(operators.get(i).get("value").equals("-")) newValue = Utils.doubleToString(new MinusOperation(leftValue, rightValue).getResult());
            }else {
                if(operators.get(i).get("value").equals("x")) newValue = Utils.doubleToString(new MultiplyOperation(leftValue, rightValue).getResult());
                if(operators.get(i).get("value").equals("÷")) newValue = Utils.doubleToString(new DivideOperation(leftValue, rightValue).getResult());
                if(operators.get(i).get("value").equals("+")) newValue = Utils.doubleToString(new PlusOperation(leftValue, rightValue).getResult());
                if(operators.get(i).get("value").equals("-")) newValue = Utils.doubleToString(new MinusOperation(leftValue, rightValue).getResult());
            }

            // calculate
            if(operators.get(i).get("value").equals("x")) {
                // handling floating..

                if(currentLeftNumber.contains(".") || currentRightNumber.contains(".")) {
                    float multiFloatingResult = Float.parseFloat(currentLeftNumber) * Float.parseFloat(currentRightNumber);
                    newValue = String.valueOf(multiFloatingResult);
                }else {
                    MultiplyOperation multiplicationResult = new MultiplyOperation(leftValue, rightValue);
                    newValue = String.valueOf(multiplicationResult.getResult());
                }

            }else if(operators.get(i).get("value").equals("÷")) {
                // handling floating..
                if(currentLeftNumber.contains(".") || currentRightNumber.contains(".")) {
                    float diviFloatingResult = Float.parseFloat(currentLeftNumber) / Float.parseFloat(currentRightNumber);
                    newValue = String.valueOf(diviFloatingResult);
                }else {
                    DivideOperation divideOperation = new DivideOperation(leftValue, rightValue);
                    newValue = String.valueOf(divideOperation.getResult());
                }
            }else if(operators.get(i).get("value").equals("+")) {
                // handling floating..
                if(currentLeftNumber.contains(".") || currentRightNumber.contains(".")) {
                    float addFloatingResult = Float.parseFloat(currentLeftNumber) + Float.parseFloat(currentRightNumber);
                    newValue = String.valueOf(addFloatingResult);
                }else {
                    PlusOperation plusOperation= new PlusOperation(leftValue, rightValue);
                    newValue = String.valueOf(plusOperation.getResult());
                }
            }else if(operators.get(i).get("value").equals("-")) {
                // handling floating..
                if(currentLeftNumber.contains(".") || currentRightNumber.contains(".")) {
                    float minusFloatinResult = Float.parseFloat(currentLeftNumber) - Float.parseFloat(currentRightNumber);
                    newValue = String.valueOf(minusFloatinResult);
                }else {
                    MinusOperation minusOperation = new MinusOperation(leftValue, rightValue);
                    newValue = String.valueOf(minusOperation.getResult());
                }
            }

            Log.i("Val", newValue);

            // Replacement du signe A // MINUS
            newValue = newValue.replace('-', 'A');

            //remove the bloc of calcul in formula: exemple -> "2 x 2"
            String calculBloc = currentLeftNumber + operators.get(i).get("value") + currentRightNumber;
            int deletePos = -1;
            for(int j = 0; j < calculBloc.length(); j++) {
                if(deletePos == -1) {
                    deletePos = Integer.parseInt(operators.get(i).get("pos")) - currentLeftNumber.length();
                }
                currentComputation.remove(deletePos);
            }

            // insert newvalue in the formla: exemple -> "4"
            for(int k = 0; k < newValue.length(); k ++) {
                String reverseValue = new StringBuilder(newValue).reverse().toString();     // New value reverted
                currentComputation.add(deletePos, String.valueOf(reverseValue.charAt(k)));
            }

            newPosition = currentComputation.size() - newValue.length();
            //change the position of each operator
            for(HashMap<String, String> o : operators) {
                o.put("pos", String.valueOf(Integer.parseInt(o.get("pos")) - (calculBloc.length() - newValue.length())));
            }
        }
    }

    /* Cette fonction va simplement effectuer les additions et les soustractions
       Si il n y a aucune opération ä effectuer, il ne fait rien
     */
    private void calculate() {
        ArrayList<HashMap<String, String>> listOperators = new ArrayList<HashMap<String, String>>();
        boolean toReplace = false;

        for(int i = 0; i < currentComputation.size(); i++) {
            if(currentComputation.get(i).toLowerCase().equals("+") || currentComputation.get(i).equals("-")) {
                toReplace = true;
                listOperators.add(new HashMap<String, String>());
                listOperators.get(listOperators.size() - 1).put("pos", String.valueOf(i));          // pos : position dans le tableau
                listOperators.get(listOperators.size() - 1).put("value", currentComputation.get(i));// values : valeur du nombre ou autres
            }
        }
        if(toReplace) replace(listOperators);
    }

    public void reset() {
        currentComputation.clear();
        currrentWritingNumber.clear();
        result = "";
        formula = "";
        lastOperation = "";
    }

    private String replaceSign(String digit) {
        if(digit.length() > 0) {
            if(digit.charAt(0) == 'A')
                return digit.replace('A', '-');
        }

        return digit;
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

    private String formatSign(String str) {
        if(str.contains("A"))
            return str.replace('A', '-');
        return str;
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
        return this.formatSign(formula);
    }

    public String getResult() {
        return this.formatSign(result);
    }
}
