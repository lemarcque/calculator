package io.capsulo.calculator.calculator.operation;

import io.capsulo.calculator.calculator.Constants;
import io.capsulo.calculator.calculator.operation.base.ComplexBlock;

/**
 * @author lemarcque
 * Description
 */

public class MinusOperation extends ComplexBlock {

    public MinusOperation(double firstValue, double secondValue) {
        super(firstValue, secondValue);
        this.type = Constants.SUBSTRACTION;
        super.setOperator(Constants.MINUS_OPERATOR);
        this.setFormula();
    }

    public double getResult() {
        return firstValue - secondeValue;
    }

    @Override
    protected void setFormula() {
        this.formula = String.valueOf(firstValue + "-" + secondeValue);
    }

}
