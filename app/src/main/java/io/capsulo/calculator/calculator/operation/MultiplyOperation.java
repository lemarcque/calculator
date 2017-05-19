package io.capsulo.calculator.calculator.operation;

import io.capsulo.calculator.calculator.Constants;
import io.capsulo.calculator.calculator.operation.base.BaseOperation;
import io.capsulo.calculator.calculator.operation.base.ComplexBlock;

/**
 * @author lemarcque
 * Description
 */

public class MultiplyOperation extends ComplexBlock {


    public MultiplyOperation(double firstValue, double secondValue) {
        super(firstValue, secondValue);
        this.type = Constants.MULTIPLICATION;
        super.setOperator(Constants.MULTIPLE_OPERATOR);
        this.setFormula();
    }

    public double getResult() {
        return firstValue * secondeValue;
    }

    @Override
    protected void setFormula() {
        this.formula = String.valueOf(firstValue + "*" + secondeValue);
    }


}
