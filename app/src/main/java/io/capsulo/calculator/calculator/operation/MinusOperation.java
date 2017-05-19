package io.capsulo.calculator.calculator.operation;

import io.capsulo.calculator.calculator.Constants;
import io.capsulo.calculator.calculator.operation.base.BaseOperation;

/**
 * @author lemarcque
 * Description
 */

public class MinusOperation extends BaseOperation {

    public MinusOperation(double firstValue, double secondValue) {
        super(firstValue, secondValue);
        this.type = Constants.SUBSTRACTION;
        this.operator = Constants.MINUS_OPERATOR;
        this.setFormula();
    }

    public double getResult() {
        return firstValue - secondeValue;
    }

    private void setFormula() {
        this.formula = String.valueOf(firstValue + "-" + secondeValue);
    }

}
