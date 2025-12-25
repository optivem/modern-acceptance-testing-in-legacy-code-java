package com.optivem.eshop.systemtest.core.gherkin.when;

import com.optivem.eshop.systemtest.core.SystemDsl;
import com.optivem.eshop.systemtest.core.gherkin.then.ThenClause;

public class CancelOrderBuilder {
    private final SystemDsl app;
    private String orderNumber;

    public CancelOrderBuilder(SystemDsl app) {
        this.app = app;
    }

    public CancelOrderBuilder withOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
        return this;
    }

    public ThenClause then() {
        // Execute the cancel order
        app.shop().cancelOrder()
                .orderNumber(orderNumber)
                .execute();

        return new ThenClause(app, orderNumber);
    }
}
