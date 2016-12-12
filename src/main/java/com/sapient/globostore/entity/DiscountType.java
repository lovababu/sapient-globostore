package com.sapient.globostore.entity;

/**
 * Created by dpadal on 12/12/2016.
 */
public enum DiscountType {
    QUANTITY("Math.floor(NP/Q)  * (1/Q * DP)"), PERCENTILE("UP * p/100");

    private String expression;

    DiscountType(String expression) {
        this.expression = expression;
    }

    public String getExpression() {
        return expression;
    }
}
