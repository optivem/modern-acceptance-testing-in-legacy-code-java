package com.optivem.eshop.systemtest.core.gherkin.then;

import com.optivem.commons.dsl.ResponseVerification;
import com.optivem.eshop.systemtest.core.SystemDsl;
import com.optivem.eshop.systemtest.core.gherkin.ExecutionResultContext;

public abstract class BaseThenVerifier<TSuccessResponse, TSuccessVerification extends ResponseVerification<TSuccessResponse>> {
    protected final SystemDsl app;
    protected final ExecutionResultContext executionResult;
    protected final TSuccessVerification successVerification;

    protected BaseThenVerifier(SystemDsl app, ExecutionResultContext executionResult, TSuccessVerification successVerification) {
        this.app = app;
        this.executionResult = executionResult;
        this.successVerification = successVerification;
    }

    public BaseThenVerifier<TSuccessResponse, TSuccessVerification> and() {
        return this;
    }

    public ThenOrderVerifier<TSuccessResponse, TSuccessVerification> order(String orderNumber) {
        return new ThenOrderVerifier<>(app, executionResult, orderNumber, successVerification);
    }

    public ThenOrderVerifier<TSuccessResponse, TSuccessVerification> order() {
        var orderNumber = executionResult.getOrderNumber();
        if (orderNumber == null) {
            throw new IllegalStateException("Cannot verify order: no order number available from the executed operation");
        }
        return order(orderNumber);
    }

    public ThenCouponVerifier coupon(String couponCode) {
        return new ThenCouponVerifier(app, executionResult, couponCode);
    }

    public ThenCouponVerifier coupon() {
        var couponCode = executionResult.getCouponCode();
        if (couponCode == null) {
            throw new IllegalStateException("Cannot verify coupon: no coupon code available from the executed operation");
        }
        return coupon(couponCode);
    }
}
