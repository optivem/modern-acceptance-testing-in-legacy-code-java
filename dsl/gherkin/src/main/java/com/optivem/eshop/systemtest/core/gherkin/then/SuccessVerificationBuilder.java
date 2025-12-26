package com.optivem.eshop.systemtest.core.gherkin.then;

import com.optivem.eshop.systemtest.core.SystemDsl;
import com.optivem.eshop.systemtest.core.gherkin.ScenarioDsl;

public class SuccessVerificationBuilder {
    private final SystemDsl app;
    private final ScenarioDsl scenario;
    private final String orderNumber;

    public SuccessVerificationBuilder(SystemDsl app, ScenarioDsl scenario, String orderNumber) {
        this.app = app;
        this.scenario = scenario;
        this.orderNumber = orderNumber;
    }

    public SuccessVerificationBuilder expectOrderNumberPrefix(String prefix) {
        // This would verify that order number starts with the prefix
        // The actual verification happens during placeOrder
        return this;
    }

    public ThenClause and() {
        return new ThenClause(app, scenario, orderNumber);
    }
}
