package com.optivem.eshop.systemtest.core.gherkin.then;

import com.optivem.eshop.systemtest.core.SystemDsl;
import com.optivem.eshop.systemtest.core.gherkin.ScenarioDsl;
import com.optivem.eshop.systemtest.core.shop.dsl.commands.base.ShopUseCaseResult;

import static com.optivem.eshop.systemtest.core.gherkin.GherkinDefaults.DEFAULT_ORDER_NUMBER;

public class ThenClause {
    private final SystemDsl app;
    private final ScenarioDsl scenario;
    private final String orderNumber;
    private final ShopUseCaseResult<?, ?> result;

    public ThenClause(SystemDsl app, ScenarioDsl scenario, String orderNumber) {
        this(app, scenario, orderNumber, null);
    }

    public ThenClause(SystemDsl app, ScenarioDsl scenario, String orderNumber, ShopUseCaseResult<?, ?> result) {
        this.app = app;
        this.scenario = scenario;
        this.orderNumber = orderNumber;
        this.result = result;
    }

    public SuccessVerificationBuilder shouldSucceed() {
        if (result == null) {
            throw new IllegalStateException("Cannot verify success: no operation was executed");
        }
        scenario.markAsExecuted();
        var successVerification = result.shouldSucceed();
        return new SuccessVerificationBuilder(this, successVerification);
    }

    public FailureVerificationBuilder shouldFail() {
        scenario.markAsExecuted();
        return new FailureVerificationBuilder(this, result);
    }

    public OrderVerificationBuilder order(String orderNumber) {
        scenario.markAsExecuted();
        return new OrderVerificationBuilder(app, orderNumber);
    }

    public OrderVerificationBuilder order() {
        return order(this.orderNumber != null ? this.orderNumber : DEFAULT_ORDER_NUMBER);
    }
}
