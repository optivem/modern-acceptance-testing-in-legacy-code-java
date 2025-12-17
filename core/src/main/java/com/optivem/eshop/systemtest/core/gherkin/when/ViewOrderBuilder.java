package com.optivem.eshop.systemtest.core.gherkin.when;

import com.optivem.eshop.systemtest.core.SystemDsl;
import com.optivem.eshop.systemtest.core.gherkin.then.ThenClause;

public class ViewOrderBuilder {
    private final SystemDsl app;
    private String orderNumber;

    public ViewOrderBuilder(SystemDsl app) {
        this.app = app;
    }

    public ViewOrderBuilder withOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
        return this;
    }

    public ThenClause then() {
        var result = app.shop().viewOrder()
                .orderNumber(orderNumber)
                .execute();

        return new ThenClause(app, orderNumber, result);
    }
}
