package io.capsulo.calculator.calculator.operation.base;

/**
 * Created by lemarcque on 19.05.17.
 */

public class BaseOperation {

    protected String type;
    protected String formula;
    protected double firstValue;
    protected double secondeValue;
    protected String operator;

    public BaseOperation(double firstValue, double secondeValue) {
        this.firstValue = firstValue;
        this.secondeValue = secondeValue;
    }

    public double getFirstValue() {
        return this.firstValue;
    }

    public double getSecondeValue() {
        return this.secondeValue;
    }

    public String getFormula() {
        return this.formula;
    }

    public String getOperator() {
        return this.operator;
    }
}
