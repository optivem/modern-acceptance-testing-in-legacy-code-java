package com.optivem.eshop.systemtest.dsl.core.scenario.when.steps;

import com.optivem.eshop.systemtest.dsl.core.app.VoidVerification;
import com.optivem.eshop.systemtest.dsl.core.app.AppDsl;
import com.optivem.eshop.systemtest.dsl.core.scenario.ExecutionResult;
import com.optivem.eshop.systemtest.dsl.core.scenario.ExecutionResultBuilder;
import com.optivem.eshop.systemtest.dsl.port.scenario.when.steps.WhenCancelOrder;

import static com.optivem.eshop.systemtest.dsl.core.scenario.ScenarioDefaults.DEFAULT_ORDER_NUMBER;

public class WhenCancelOrderImpl extends BaseWhenStep<Void, VoidVerification> implements WhenCancelOrder {
    private String orderNumber;

    public WhenCancelOrderImpl(AppDsl app) {
        super(app);
        withOrderNumber(DEFAULT_ORDER_NUMBER);
    }

    public WhenCancelOrderImpl withOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
        return this;
    }

    @Override
    protected ExecutionResult<Void, VoidVerification> execute(AppDsl app) {
        var result = app.shop().cancelOrder()
                .orderNumber(orderNumber)
                .execute();

        return new ExecutionResultBuilder<>(result)
                .orderNumber(orderNumber)
                .build();
    }
}

