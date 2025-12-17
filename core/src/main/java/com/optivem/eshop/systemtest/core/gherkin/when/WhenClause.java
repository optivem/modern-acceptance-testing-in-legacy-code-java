package com.optivem.eshop.systemtest.core.gherkin.when;

import com.optivem.eshop.systemtest.core.SystemDsl;

public class WhenClause {
    private final SystemDsl app;

    public WhenClause(SystemDsl app) {
        this.app = app;
    }

    public PlaceOrderBuilder placeOrder() {
        return new PlaceOrderBuilder(app);
    }

    public CancelOrderBuilder cancelOrder() {
        return new CancelOrderBuilder(app);
    }

    public ViewOrderBuilder viewOrder() {
        return new ViewOrderBuilder(app);
    }
}
