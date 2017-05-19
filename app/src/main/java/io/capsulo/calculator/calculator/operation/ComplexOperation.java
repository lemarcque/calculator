package io.capsulo.calculator.calculator.operation;

import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

import io.capsulo.calculator.calculator.Constants;
import io.capsulo.calculator.calculator.Digit;
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
    private int nbBlock;
    private ArrayList<ComplexBlock> complexBlocks;

    public ComplexOperation(ArrayList<String> formula) {
        this.currentFormula = formula;
        Log.i("formula vlaue", formula.toString());
        listOperators = new ArrayList<HashMap<String, String>>();
        listOperatorsI = new ArrayList<HashMap<String, String>>();
        listOperatorsII = new ArrayList<HashMap<String, String>>();
        complexBlocks = new ArrayList<ComplexBlock>();
    }

    // to do...
    public double getResult() {
        findMultiDivOperator();
        return 0;
    }

    private void findMultiDivOperator() {
        // scanning
        for(int i = 0; i < currentFormula.size(); i++) {
            HashMap<String, String> operator = new HashMap<String, String>();
            operator.put("pos", String.valueOf(i));         // pos : position dans le tableau
            operator.put("value", currentFormula.get(i));   // values : valeur du nombre ou autres

            if(currentFormula.get(i).toLowerCase().equals(Constants.MULTIPLE_OPERATOR)) listOperatorsI.add(operator);
            if(currentFormula.get(i).equals(Constants.DIVIDE_OPERATOR)) listOperatorsI.add(operator);
            if(currentFormula.get(i).toLowerCase().equals(Constants.PLUS_OPERATOR)) listOperatorsII.add(operator);
            if(currentFormula.get(i).equals(Constants.MINUS_OPERATOR)) listOperatorsII.add(operator);

            listOperators.add(operator);
        }

        trim();

        Log.i("multi/divi", listOperatorsI.toString());
        Log.i("plus/minus", listOperatorsII.toString());
    }

    private void trim() {
        for(int i = 0; i < listOperatorsI.size(); i++) {
            ComplexBlock complexBlock = new ComplexBlock();
            complexBlock.calculValues(TextUtils.join("", currentFormula), listOperatorsI.get(i));
            double result = 0;


            if(complexBlock.getOperator().equals(Constants.PLUS_OPERATOR)) {
                PlusOperation plusOperation = (PlusOperation) complexBlock;
                complexBlock = plusOperation;
                result = plusOperation.getResult();
            }else if(complexBlock.getOperator().equals(Constants.MINUS_OPERATOR)) {
                MinusOperation minusOperation = (MinusOperation) complexBlock;
                complexBlock = minusOperation;
                result = minusOperation.getResult();
            }else if(complexBlock.getOperator().equals(Constants.MULTIPLE_OPERATOR)) {
                Log.i("a", String.valueOf(complexBlock.getLeftValue()));
                Log.i("b", String.valueOf(complexBlock.getRightValue()));
                MultiplyOperation multiplyOperation = (MultiplyOperation) complexBlock;
                Log.i("z", String.valueOf(multiplyOperation.getFirstValue()));
                Log.i("y", String.valueOf(multiplyOperation.getSecondeValue()));
                complexBlock = multiplyOperation;
                result = multiplyOperation.getResult();
            }else if(complexBlock.getOperator().equals(Constants.DIVIDE_OPERATOR)) {
                DivideOperation divideOperation = (DivideOperation) complexBlock;
                complexBlock = divideOperation;
                result = divideOperation.getResult();
            }

            // Gestion des flottants later ....

            // Replacement du signe A // MINUS
            String resultValue = Digit.replaceSignA(String.valueOf(result));

            Log.i("result", resultValue);

            //remove the bloc of calcul in formula: exemple -> "2 x 2"
            String bloc = String.valueOf(complexBlock);
            int delPos = -1;
            for(int j = 0; j < bloc.length(); j++) {
                if(delPos == -1) delPos = Integer.parseInt(listOperatorsI.get(i).get("pos")) - String.valueOf(complexBlock.getLeftValue()).length();
                currentFormula.remove(delPos);
            }

            // insert newvalue in the formla: exemple -> "4"
            for(int k = 0; k < resultValue.length(); k ++) {
                String reverseValue = new StringBuilder(resultValue).reverse().toString();     // New value reverted
                currentFormula.add(delPos, String.valueOf(reverseValue.charAt(k)));
            }

            int position = currentFormula.size() - resultValue.length();
            //change the position of each operator
            for(HashMap<String, String> o : listOperatorsI) {
                o.put("pos", String.valueOf(Integer.parseInt(o.get("pos")) - (bloc.length() - resultValue.length())));
            }

            //complexBlock.setFirstValue(0);
            //complexBlock.setSecondeValue(0);
            complexBlocks.add(complexBlock);
        }
    }
}
