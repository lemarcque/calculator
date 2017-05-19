package io.capsulo.calculator.calculator.operation;

import io.capsulo.calculator.calculator.Constants;
import io.capsulo.calculator.calculator.operation.base.ComplexBlock;

/**
 * @author lemarcque
 * Description
 */

public class DivideOperation extends ComplexBlock {

    public DivideOperation(double firstValue, double secondValue) {
        super(firstValue, secondValue);
        this.type = Constants.DIVISION;
        super.setOperator(Constants.DIVIDE_OPERATOR);
        this.setFormula();
    }

    public double getResult() {
        return firstValue / secondeValue;
    }

    @Override
    protected void setFormula() {
        this.formula = String.valueOf(firstValue + "/" + secondeValue);
    }
}
