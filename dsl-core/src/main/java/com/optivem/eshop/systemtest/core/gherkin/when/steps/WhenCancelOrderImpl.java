package com.optivem.eshop.systemtest.core.gherkin.when.steps;

import com.optivem.commons.dsl.VoidVerification;
import com.optivem.eshop.systemtest.core.system.SystemDsl;
import com.optivem.eshop.systemtest.core.gherkin.ExecutionResult;
import com.optivem.eshop.systemtest.core.gherkin.ExecutionResultBuilder;
import com.optivem.eshop.systemtest.dsl.api.when.steps.WhenCancelOrderPort;

import static com.optivem.eshop.systemtest.core.gherkin.GherkinDefaults.DEFAULT_ORDER_NUMBER;

public class WhenCancelOrderImpl extends BaseWhenStep<Void, VoidVerification> implements WhenCancelOrderPort {
    private String orderNumber;

    public WhenCancelOrderImpl(SystemDsl app) {
        super(app);
        withOrderNumber(DEFAULT_ORDER_NUMBER);
    }

    public WhenCancelOrderImpl withOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
        return this;
    }

    @Override
    protected ExecutionResult<Void, VoidVerification> execute(SystemDsl app) {
        var result = app.shop().cancelOrder()
                .orderNumber(orderNumber)
                .execute();

        return new ExecutionResultBuilder<>(result)
                .orderNumber(orderNumber)
                .build();
    }
}