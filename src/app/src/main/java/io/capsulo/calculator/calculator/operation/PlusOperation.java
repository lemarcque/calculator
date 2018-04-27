package io.capsulo.calculator.calculator.operation;

import io.capsulo.calculator.calculator.Constants;
import io.capsulo.calculator.calculator.operation.base.ComplexBlock;

/**
 * @author lemarcque
 * Description
 */

public class PlusOperation extends ComplexBlock {

    public PlusOperation(double firstValue, double secondValue) {
        super(firstValue, secondValue);
        this.type = Constants.ADITION;
        super.setOperator(Constants.PLUS_OPERATOR);
        this.setFormula();
    }

    public double getResult() {
        return firstValue + secondeValue;
    }

    @Override
    protected void setFormula() {
        this.formula = String.valueOf(firstValue + "+" + secondeValue);
    }
}
