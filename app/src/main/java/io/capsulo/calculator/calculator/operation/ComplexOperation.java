package io.capsulo.calculator.calculator.operation;

import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

import io.capsulo.calculator.calculator.Constants;
import io.capsulo.calculator.calculator.Digit;
import io.capsulo.calculator.calculator.Utils;
import io.capsulo.calculator.calculator.operation.base.ComplexBlock;

/**
 * @author lemarcque
 */

public class ComplexOperation implements Operation{

    private ArrayList<String> formula;
    private ArrayList<String> currentFormula;
    private ArrayList<HashMap<String, String>> listOperators;
    private ArrayList<HashMap<String, String>> listOperatorsI;
    private ArrayList<HashMap<String, String>> listOperatorsII;
    private ArrayList<ComplexBlock> complexBlocks;

    public ComplexOperation(ArrayList<String> formula) {
        this.currentFormula = formula;
        Log.i("formula vlaue", formula.toString());
        listOperators = new ArrayList<HashMap<String, String>>();
        listOperatorsI = new ArrayList<HashMap<String, String>>();
        listOperatorsII = new ArrayList<HashMap<String, String>>();
        complexBlocks = new ArrayList<ComplexBlock>();
    }

    public double getResult() {
        findMultiDivOperator();
        String strResult = Digit.replaceSign(TextUtils.join("", currentFormula));
        return Double.parseDouble(strResult);
    }

    private void findMultiDivOperator() {
        // scanning
        for(int i = 0; i < currentFormula.size(); i++) {
            HashMap<String, String> operator = new HashMap<>();
            operator.put("pos", String.valueOf(i));         // pos : position dans le tableau
            operator.put("value", currentFormula.get(i));   // values : valeur du nombre ou autres

            if(currentFormula.get(i).toLowerCase().equals(Constants.MULTIPLE_OPERATOR)) listOperatorsI.add(operator);
            if(currentFormula.get(i).equals(Constants.DIVIDE_OPERATOR)) listOperatorsI.add(operator);

            if(currentFormula.get(i).toLowerCase().equals(Constants.PLUS_OPERATOR)) listOperatorsII.add(operator);
            if(currentFormula.get(i).equals(Constants.MINUS_OPERATOR)) listOperatorsII.add(operator);

            listOperators.add(operator);
        }

        trim(listOperatorsI);
        trim(listOperatorsII);
    }

    private void trim(ArrayList<HashMap<String, String>> operators) {
        for(int i = 0; i < operators.size(); i++) {
            ComplexBlock complexBlock = new ComplexBlock();
            complexBlock.calculValues(TextUtils.join("", currentFormula), operators.get(i));
            double result = 0;

            switch (complexBlock.getOperator()) {
                //result = ((MultiplyOperation) complexBlock).getResult();    -   casting not working

                case Constants.PLUS_OPERATOR:
                    result = new PlusOperation(complexBlock.getLeftValue(), complexBlock.getRightValue()).getResult();
                    break;
                case Constants.MINUS_OPERATOR:
                    result = new MinusOperation(complexBlock.getLeftValue(), complexBlock.getRightValue()).getResult();
                    break;
                case Constants.MULTIPLE_OPERATOR:
                    result = new MultiplyOperation(complexBlock.getLeftValue(), complexBlock.getRightValue()).getResult();
                    break;
                case Constants.DIVIDE_OPERATOR:
                    result = new DivideOperation(complexBlock.getLeftValue(), complexBlock.getRightValue()).getResult();
                    break;
            }


            // Gestion des flottants later ....

            // Replacement du signe A // MINUS
            String resultValue = Utils.doubleToString(result);
            resultValue = Digit.replaceSignA(resultValue);
            String leftValue = Utils.doubleToString(complexBlock.getLeftValue());


            //remove the bloc of calcul in formula: exemple -> "2 x 2"
            String bloc = String.valueOf(complexBlock.getFormula());

            int delPos = -1;
            for(int j = 0; j < bloc.length(); j++) {
                if(delPos == -1) delPos = Integer.parseInt(operators.get(i).get("pos")) - leftValue.length();
                Log.i("-", currentFormula.toString());
                currentFormula.remove(delPos);
            }

            Log.i("..", currentFormula.toString());

            // insert newvalue in the formla: exemple -> "4"
            for(int k = 0; k < resultValue.length(); k ++) {
                String reverseValue = new StringBuilder(resultValue).reverse().toString();     // New value reverted
                currentFormula.add(delPos, String.valueOf(reverseValue.charAt(k)));
            }

            Log.i(";;", currentFormula.toString());

            int position = currentFormula.size() - resultValue.length();
            //change the position of each operator
            for(HashMap<String, String> o : operators) {
                o.put("pos", String.valueOf(Integer.parseInt(o.get("pos")) - (bloc.length() - resultValue.length())));
            }

            complexBlocks.add(complexBlock);
        }
    }
}
