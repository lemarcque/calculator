package io.capsulo.calculator.calculator.operation;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

import io.capsulo.calculator.calculator.Constants;
import io.capsulo.calculator.calculator.operation.base.ComplexBlock;

/**
 * @author lemarcque
 */

public class ComplexOperation implements Operation{

    private String formula;
    private ArrayList<String> currentFormula;
    private ArrayList<HashMap<String, String>> listOperators;
    private ArrayList<HashMap<String, String>> listOperatorsI;
    private ArrayList<HashMap<String, String>> listOperatorsII;
    private int nbBlock;
    private ArrayList<ComplexBlock> complexBlocks;

    public ComplexOperation(ArrayList<String> formula) {
        this.currentFormula = formula;
        listOperators = new ArrayList<HashMap<String, String>>();
        listOperatorsI = new ArrayList<HashMap<String, String>>();
        listOperatorsII = new ArrayList<HashMap<String, String>>();
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

        for(int i = 0; i < listOperatorsI.size(); i++) replace();
        for(int j = 0; j < listOperatorsI.size(); j++) replace();

        Log.i("multi/divi", listOperatorsI.toString());
        Log.i("plus/minus", listOperatorsII.toString());
    }

    private void replace() {

    }
}
