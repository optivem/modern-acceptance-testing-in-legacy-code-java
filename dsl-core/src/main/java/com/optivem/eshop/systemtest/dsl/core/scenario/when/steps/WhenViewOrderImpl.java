package com.optivem.eshop.systemtest.dsl.core.scenario.when.steps;

import static com.optivem.eshop.systemtest.dsl.core.scenario.ScenarioDefaults.DEFAULT_ORDER_NUMBER;

import com.optivem.eshop.systemtest.dsl.core.app.AppDsl;
import com.optivem.eshop.systemtest.dsl.core.scenario.ExecutionResult;
import com.optivem.eshop.systemtest.dsl.core.scenario.ExecutionResultBuilder;
import com.optivem.eshop.systemtest.driver.port.shop.dtos.ViewOrderResponse;
import com.optivem.eshop.systemtest.dsl.port.scenario.when.steps.WhenViewOrder;
import com.optivem.eshop.systemtest.dsl.core.app.shop.usecases.ViewOrderVerification;

public class WhenViewOrderImpl extends BaseWhenStep<ViewOrderResponse, ViewOrderVerification> implements WhenViewOrder {
    private String orderNumber;

    public WhenViewOrderImpl(AppDsl app) {
        super(app);
        withOrderNumber(DEFAULT_ORDER_NUMBER);
    }

    public WhenViewOrderImpl withOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
        return this;
    }

    @Override
    protected ExecutionResult<ViewOrderResponse, ViewOrderVerification> execute(AppDsl app) {
        var result = app.shop().viewOrder()
                .orderNumber(orderNumber)
                .execute();

        return new ExecutionResultBuilder<>(result)
                .orderNumber(orderNumber)
                .build();
    }
}

