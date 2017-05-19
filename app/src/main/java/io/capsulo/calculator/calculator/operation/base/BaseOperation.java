package io.capsulo.calculator.calculator.operation.base;

/**
 * Created by lemarcque on 19.05.17.
 */

public class BaseOperation {

    protected String type;
    protected String formula;
    protected double firstValue;
    protected double secondeValue;
    private String operator;

    public BaseOperation() {

    }

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

    public double getResult() {
        return 0;
    }

    public void setFirstValue(double v) {
        firstValue = v;
        this.setFormula();
    }

    public void setSecondeValue(double v) {
        secondeValue = v;
        this.setFormula();
    }

    public void setOperator(String o) {
        this.operator = o;
    }

    protected void setFormula() {

    }
}
