package io.capsulo.calculator.calculator.operation;

import android.text.TextUtils;
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
    private int integerOffset;
    private int lastOffsetPos;

    public ComplexOperation(ArrayList<String> formula) {
        this.currentFormula = formula;
        listOperators = new ArrayList<HashMap<String, String>>();
        listOperatorsI = new ArrayList<HashMap<String, String>>();
        listOperatorsII = new ArrayList<HashMap<String, String>>();
        complexBlocks = new ArrayList<ComplexBlock>();
        integerOffset = 0;
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
        if(operators.size() > 0) {
            for(HashMap<String, String> op : operators) {
                if(lastOffsetPos < Integer.parseInt(op.get("pos")))
                    op.put("pos", String.valueOf(Integer.parseInt(op.get("pos")) - integerOffset));
            }
        }

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

            // Replacement du signe A // MINUS
            String resultValue = Utils.doubleToString(result);
            resultValue = Digit.replaceSignA(resultValue);
            String leftValue = Utils.doubleToString(complexBlock.getLeftValue());

            //remove the bloc of calcul (2x2) in formula (2x2+1). output => (+1)
            String bloc = String.valueOf(complexBlock.getFormula());

            int delPos = -1;
            for(int j = 0; j < bloc.length(); j++) {
                if(delPos == -1) delPos = Integer.parseInt(operators.get(i).get("pos")) - leftValue.length();
                currentFormula.remove(delPos);
            }

            // insert newvalue ("4") in the formla:. output => (4+1)
            for(int k = 0; k < resultValue.length(); k ++) {
                String reverseValue = new StringBuilder(resultValue).reverse().toString();     // New value reverted
                currentFormula.add(delPos, String.valueOf(reverseValue.charAt(k)));
            }

            //change the position of each operator
            for(HashMap<String, String> op : operators) {
                int offset = (bloc.length() - resultValue.length());
                integerOffset += offset;
                int updatedPos = Integer.parseInt(op.get("pos")) - offset;
                if(op.get("value").equals(Constants.MULTIPLE_OPERATOR) || op.get("value").equals(Constants.DIVIDE_OPERATOR))
                    lastOffsetPos = Integer.parseInt(op.get("pos"));

                op.put("pos", String.valueOf(updatedPos));
            }

            complexBlocks.add(complexBlock);
        }
    }
}
