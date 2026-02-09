package com.optivem.eshop.systemtest.core.gherkin.then;

import com.optivem.commons.dsl.ResponseVerification;

public abstract class BaseThenBuilder<TSuccessResponse, TSuccessVerification extends ResponseVerification<TSuccessResponse>> {
    private final ThenClause<TSuccessResponse, TSuccessVerification> thenClause;

    protected BaseThenBuilder(ThenClause<TSuccessResponse, TSuccessVerification> thenClause) {
        this.thenClause = thenClause;
    }

    public BaseThenBuilder<TSuccessResponse, TSuccessVerification> and() {
        return this;
    }

    public ThenOrderBuilder<TSuccessResponse, TSuccessVerification> order(String orderNumber) {
        return new ThenOrderBuilder<>(thenClause, thenClause.getApp(), orderNumber);
    }

    public ThenOrderBuilder<TSuccessResponse, TSuccessVerification> order() {
        var orderNumber = thenClause.getExecutionResult().getOrderNumber();
        if (orderNumber == null) {
            throw new IllegalStateException("Cannot verify order: no order number available from the executed operation");
        }
        return order(orderNumber);
    }

    public ThenCouponBuilder<TSuccessResponse, TSuccessVerification> coupon(String couponCode) {
        return new ThenCouponBuilder<>(thenClause, thenClause.getApp(), couponCode);
    }

    public ThenCouponBuilder<TSuccessResponse, TSuccessVerification> coupon() {
        var couponCode = thenClause.getExecutionResult().getCouponCode();
        if (couponCode == null) {
            throw new IllegalStateException("Cannot verify coupon: no coupon code available from the executed operation");
        }
        return coupon(couponCode);
    }
}
