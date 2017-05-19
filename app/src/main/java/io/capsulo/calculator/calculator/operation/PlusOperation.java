package io.capsulo.calculator.calculator.operation;

import io.capsulo.calculator.calculator.Constants;
import io.capsulo.calculator.calculator.operation.base.BaseOperation;

/**
 * @author lemarcque
 * Description
 */

public class PlusOperation extends BaseOperation {

    public PlusOperation(double firstValue, double secondValue) {
        super(firstValue, secondValue);
        this.type = Constants.ADITION;
        this.operator = Constants.PLUS_OPERATOR;
        this.setFormula();
    }

    public double getResult() {
        return firstValue + secondeValue;
    }

    private void setFormula() {
        this.formula = String.valueOf(firstValue + "+" + secondeValue);
    }
}
