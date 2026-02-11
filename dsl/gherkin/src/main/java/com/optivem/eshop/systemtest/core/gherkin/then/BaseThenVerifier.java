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
        return order(executionResult.getOrderNumber());
    }

    public ThenCouponVerifier coupon(String couponCode) {
        return new ThenCouponVerifier(app, executionResult, couponCode);
    }

    public ThenCouponVerifier coupon() {
        return coupon(executionResult.getCouponCode());
    }
}
