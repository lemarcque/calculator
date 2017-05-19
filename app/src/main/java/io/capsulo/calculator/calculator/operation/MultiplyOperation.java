package io.capsulo.calculator.calculator.operation;

import io.capsulo.calculator.calculator.Constants;
import io.capsulo.calculator.calculator.operation.base.BaseOperation;

/**
 * @author lemarcque
 * Description
 */

public class MultiplyOperation extends BaseOperation {

    public MultiplyOperation(double firstValue, double secondValue) {
        super(firstValue, secondValue);
        this.type = Constants.MULTIPLICATION;
        this.operator = Constants.MULTIPLE_OPERATOR;
        this.setFormula();
    }

    public double getResult() {
        return firstValue * secondeValue;
    }

    private void setFormula() {
        this.formula = String.valueOf(firstValue + "*" + secondeValue);
    }

}
