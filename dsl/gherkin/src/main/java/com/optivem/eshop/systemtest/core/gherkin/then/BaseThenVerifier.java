package com.optivem.eshop.systemtest.core.gherkin.then;

import com.optivem.eshop.systemtest.core.SystemDsl;
import com.optivem.eshop.systemtest.core.gherkin.ExecutionResultContext;

public abstract class BaseThenVerifier {
    private final SystemDsl app;
    private final ExecutionResultContext executionResult;

    protected BaseThenVerifier(SystemDsl app, ExecutionResultContext executionResult) {
        this.app = app;
        this.executionResult = executionResult;
    }

    public BaseThenVerifier and() {
        return this;
    }

    public ThenOrderVerifier order(String orderNumber) {
        return new ThenOrderVerifier(app, executionResult, orderNumber);
    }

    public ThenOrderVerifier order() {
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
