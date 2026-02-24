package com.optivem.eshop.systemtest.dsl.core.gherkin.when.steps;

import static com.optivem.eshop.systemtest.dsl.core.gherkin.GherkinDefaults.DEFAULT_ORDER_NUMBER;

import com.optivem.eshop.systemtest.dsl.core.system.SystemDsl;
import com.optivem.eshop.systemtest.dsl.core.gherkin.ExecutionResult;
import com.optivem.eshop.systemtest.dsl.core.gherkin.ExecutionResultBuilder;
import com.optivem.eshop.systemtest.driver.api.shop.dtos.orders.ViewOrderResponse;
import com.optivem.eshop.systemtest.dsl.api.when.steps.WhenViewOrderPort;
import com.optivem.eshop.systemtest.dsl.core.system.shop.dsl.usecases.ViewOrderVerification;

public class WhenViewOrderImpl extends BaseWhenStep<ViewOrderResponse, ViewOrderVerification> implements WhenViewOrderPort {
    private String orderNumber;

    public WhenViewOrderImpl(SystemDsl app) {
        super(app);
        withOrderNumber(DEFAULT_ORDER_NUMBER);
    }

    public WhenViewOrderImpl withOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
        return this;
    }

    @Override
    protected ExecutionResult<ViewOrderResponse, ViewOrderVerification> execute(SystemDsl app) {
        var result = app.shop().viewOrder()
                .orderNumber(orderNumber)
                .execute();

        return new ExecutionResultBuilder<>(result)
                .orderNumber(orderNumber)
                .build();
    }
}