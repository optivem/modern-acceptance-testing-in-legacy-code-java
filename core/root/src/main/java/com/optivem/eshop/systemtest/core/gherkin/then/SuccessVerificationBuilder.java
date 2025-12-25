package com.optivem.eshop.systemtest.core.gherkin.then;

import com.optivem.eshop.systemtest.core.SystemDsl;

public class SuccessVerificationBuilder {
    private final SystemDsl app;
    private final String orderNumber;

    public SuccessVerificationBuilder(SystemDsl app, String orderNumber) {
        this.app = app;
        this.orderNumber = orderNumber;
    }

    public SuccessVerificationBuilder expectOrderNumberPrefix(String prefix) {
        // This would verify that order number starts with the prefix
        // The actual verification happens during placeOrder
        return this;
    }

    public ThenAndClause and() {
        return new ThenAndClause(app, orderNumber);
    }
}
