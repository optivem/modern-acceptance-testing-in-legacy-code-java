package com.optivem.eshop.systemtest.core.gherkin.then;

import com.optivem.eshop.systemtest.core.SystemDsl;

public class ThenAndClause {
    private final SystemDsl app;
    private final String orderNumber;

    public ThenAndClause(SystemDsl app, String orderNumber) {
        this.app = app;
        this.orderNumber = orderNumber;
    }

    public OrderVerificationBuilder order(String orderNumber) {
        return new OrderVerificationBuilder(app, orderNumber);
    }
}
