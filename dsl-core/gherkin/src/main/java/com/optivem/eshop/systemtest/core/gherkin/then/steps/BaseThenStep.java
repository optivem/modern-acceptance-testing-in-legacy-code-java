package com.optivem.eshop.systemtest.core.gherkin.then.steps;

import com.optivem.commons.dsl.ResponseVerification;
import com.optivem.eshop.systemtest.core.SystemDsl;
import com.optivem.eshop.systemtest.core.gherkin.ExecutionResultContext;

public abstract class BaseThenStep<TSuccessResponse, TSuccessVerification extends ResponseVerification<TSuccessResponse>> {
    protected final SystemDsl app;
    protected final ExecutionResultContext executionResult;
    protected final TSuccessVerification successVerification;

    protected BaseThenStep(SystemDsl app, ExecutionResultContext executionResult, TSuccessVerification successVerification) {
        this.app = app;
        this.executionResult = executionResult;
        this.successVerification = successVerification;
    }

    public BaseThenStep<TSuccessResponse, TSuccessVerification> and() {
        return this;
    }

    public ThenOrderImpl<TSuccessResponse, TSuccessVerification> order(String orderNumber) {
        return new ThenOrderImpl<>(app, executionResult, orderNumber, successVerification);
    }

    public ThenOrderImpl<TSuccessResponse, TSuccessVerification> order() {
        if (executionResult.getOrderNumber() == null) {
            throw new IllegalStateException("Cannot verify order: no order number available from the executed operation");
        }
        return order(executionResult.getOrderNumber());
    }

    public ThenCouponImpl<TSuccessResponse, TSuccessVerification> coupon(String couponCode) {
        return new ThenCouponImpl<>(app, executionResult, couponCode, successVerification);
    }

    public ThenCouponImpl<TSuccessResponse, TSuccessVerification> coupon() {
        if (executionResult.getCouponCode() == null) {
            throw new IllegalStateException("Cannot verify coupon: no coupon code available from the executed operation");
        }
        return coupon(executionResult.getCouponCode());
    }
}
