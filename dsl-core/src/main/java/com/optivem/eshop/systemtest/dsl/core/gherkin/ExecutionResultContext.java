package com.optivem.eshop.systemtest.dsl.core.gherkin;

public class ExecutionResultContext {
    private final String orderNumber;
    private final String couponCode;

    public ExecutionResultContext(String orderNumber, String couponCode) {
        this.orderNumber = orderNumber;
        this.couponCode = couponCode;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public String getCouponCode() {
        return couponCode;
    }
}
