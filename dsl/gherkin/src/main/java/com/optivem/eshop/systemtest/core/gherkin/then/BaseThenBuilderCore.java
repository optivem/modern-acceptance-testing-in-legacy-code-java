package com.optivem.eshop.systemtest.core.gherkin.then;

public abstract class BaseThenBuilderCore {
    private final ThenClauseContext thenClauseContext;

    protected BaseThenBuilderCore(ThenClauseContext thenClauseContext) {
        this.thenClauseContext = thenClauseContext;
    }

    public BaseThenBuilderCore and() {
        return this;
    }

    public ThenOrderBuilder order(String orderNumber) {
        return new ThenOrderBuilder(thenClauseContext, orderNumber);
    }

    public ThenOrderBuilder order() {
        var orderNumber = thenClauseContext.getExecutionResult().getOrderNumber();
        if (orderNumber == null) {
            throw new IllegalStateException("Cannot verify order: no order number available from the executed operation");
        }
        return order(orderNumber);
    }

    public ThenCouponBuilder coupon(String couponCode) {
        return new ThenCouponBuilder(thenClauseContext, couponCode);
    }

    public ThenCouponBuilder coupon() {
        var couponCode = thenClauseContext.getExecutionResult().getCouponCode();
        if (couponCode == null) {
            throw new IllegalStateException("Cannot verify coupon: no coupon code available from the executed operation");
        }
        return coupon(couponCode);
    }
}
