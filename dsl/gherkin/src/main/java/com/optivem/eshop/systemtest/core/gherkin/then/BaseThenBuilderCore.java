package com.optivem.eshop.systemtest.core.gherkin.then;

import com.optivem.eshop.systemtest.core.SystemDsl;
import com.optivem.eshop.systemtest.core.gherkin.ExecutionResultContext;
import com.optivem.eshop.systemtest.core.gherkin.then.state.ThenCouponBuilder;
import com.optivem.eshop.systemtest.core.gherkin.then.state.ThenOrderBuilder;

public abstract class BaseThenBuilderCore {
    private final SystemDsl app;
    private final ExecutionResultContext executionResult;

    protected BaseThenBuilderCore(SystemDsl app, ExecutionResultContext executionResult) {
        this.app = app;
        this.executionResult = executionResult;
    }

    public BaseThenBuilderCore and() {
        return this;
    }

    public ThenOrderBuilder order(String orderNumber) {
        return new ThenOrderBuilder(app, executionResult, orderNumber);
    }

    public ThenOrderBuilder order() {
        var orderNumber = executionResult.getOrderNumber();
        if (orderNumber == null) {
            throw new IllegalStateException("Cannot verify order: no order number available from the executed operation");
        }
        return order(orderNumber);
    }

    public ThenCouponBuilder coupon(String couponCode) {
        return new ThenCouponBuilder(app, executionResult, couponCode);
    }

    public ThenCouponBuilder coupon() {
        var couponCode = executionResult.getCouponCode();
        if (couponCode == null) {
            throw new IllegalStateException("Cannot verify coupon: no coupon code available from the executed operation");
        }
        return coupon(couponCode);
    }
}
