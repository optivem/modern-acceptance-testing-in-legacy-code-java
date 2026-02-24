package com.optivem.eshop.systemtest.core.gherkin.when;

import static com.optivem.eshop.systemtest.core.gherkin.GherkinDefaults.DEFAULT_ORDER_NUMBER;

import com.optivem.eshop.systemtest.core.SystemDsl;
import com.optivem.eshop.systemtest.core.gherkin.ExecutionResult;
import com.optivem.eshop.systemtest.core.gherkin.ExecutionResultBuilder;
import com.optivem.eshop.systemtest.driver.api.shop.commons.dtos.orders.ViewOrderResponse;
import com.optivem.eshop.systemtest.dsl.api.WhenViewOrderPort;
import com.optivem.eshop.systemtest.core.shop.dsl.usecases.ViewOrderVerification;

public class WhenViewOrder extends BaseWhenStep<ViewOrderResponse, ViewOrderVerification> implements WhenViewOrderPort {
    private String orderNumber;

    public WhenViewOrder(SystemDsl app) {
        super(app);
        withOrderNumber(DEFAULT_ORDER_NUMBER);
    }

    public WhenViewOrder withOrderNumber(String orderNumber) {
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