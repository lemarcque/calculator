package io.capsulo.calculator.calculator.operation;

import android.util.Log;

import java.math.BigDecimal;
import java.math.RoundingMode;

import io.capsulo.calculator.calculator.Constants;
import io.capsulo.calculator.calculator.operation.base.ComplexBlock;

/**
 * @author lemarcque
 * Description
 */

public class DivideOperation extends ComplexBlock {

    public DivideOperation(double dividend, double divisor) {
        super(dividend, divisor);
        this.type = Constants.DIVISION;
        super.setOperator(Constants.DIVIDE_OPERATOR);
        this.setFormula();
    }

    public double getResult() {
        BigDecimal dividend = new BigDecimal(firstValue);
        BigDecimal divisor = new BigDecimal(secondeValue);
        BigDecimal result = dividend.divide(divisor, 2, RoundingMode.CEILING);
        return result.doubleValue();
    }

    @Override
    protected void setFormula() {
        this.formula = String.valueOf(firstValue + "/" + secondeValue);
    }
}
