package com.optivem.eshop.systemtest.core.gherkin.then;

import com.optivem.commons.dsl.ResponseVerification;
import com.optivem.eshop.systemtest.core.SystemDsl;
import com.optivem.eshop.systemtest.core.gherkin.ExecutionResult;
import com.optivem.eshop.systemtest.core.gherkin.ScenarioDsl;

public class ThenClause<TSuccessResponse, TSuccessVerification extends ResponseVerification<TSuccessResponse>> {
    private final SystemDsl app;
    private final ExecutionResult<TSuccessResponse, TSuccessVerification> executionResult;

    public ThenClause(SystemDsl app, ScenarioDsl scenario, ExecutionResult<TSuccessResponse, TSuccessVerification> executionResult) {
        this.app = app;
        this.executionResult = executionResult;
    }

    public ThenSuccessBuilder<TSuccessResponse, TSuccessVerification> shouldSucceed() {
        if (executionResult == null) {
            throw new IllegalStateException("Cannot verify success: no operation was executed");
        }
        var successVerification = executionResult.getResult().shouldSucceed();
        return new ThenSuccessBuilder<TSuccessResponse, TSuccessVerification>(this, successVerification);
    }

    public ThenFailureBuilder<TSuccessResponse, TSuccessVerification> shouldFail() {
        return new ThenFailureBuilder<>(this, executionResult.getResult());
    }

    public ThenOrderBuilder<TSuccessResponse, TSuccessVerification> order(String orderNumber) {
        shouldSucceed();
        return new ThenOrderBuilder<>(this, app, orderNumber);
    }

    public ThenOrderBuilder<TSuccessResponse, TSuccessVerification> order() {
        var orderNumber = executionResult.getOrderNumber();

        if(orderNumber == null) {
            throw new IllegalStateException("Cannot verify order: no order number available from the executed operation");
        }

        return order(orderNumber);
    }

    public ThenCouponBuilder<TSuccessResponse, TSuccessVerification> coupon(String couponCode) {
        shouldSucceed();
        return new ThenCouponBuilder<>(this, app, couponCode);
    }

    public ThenCouponBuilder<TSuccessResponse, TSuccessVerification> coupon() {
        var couponCode = executionResult.getCouponCode();

        if(couponCode == null) {
            throw new IllegalStateException("Cannot verify coupon: no coupon code available from the executed operation");
        }

        return coupon(couponCode);
    }
}
