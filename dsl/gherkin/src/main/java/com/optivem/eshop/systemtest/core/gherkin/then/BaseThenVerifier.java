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
        if (executionResult.getOrderNumber() == null) {
            throw new IllegalStateException("Cannot verify order: no order number available from the executed operation");
        }
        return order(executionResult.getOrderNumber());
    }

    public ThenCouponVerifier<TSuccessResponse, TSuccessVerification> coupon(String couponCode) {
        return new ThenCouponVerifier<>(app, executionResult, couponCode, successVerification);
    }

    public ThenCouponVerifier<TSuccessResponse, TSuccessVerification> coupon() {
        if (executionResult.getCouponCode() == null) {
            throw new IllegalStateException("Cannot verify coupon: no coupon code available from the executed operation");
        }
        return coupon(executionResult.getCouponCode());
    }
}
