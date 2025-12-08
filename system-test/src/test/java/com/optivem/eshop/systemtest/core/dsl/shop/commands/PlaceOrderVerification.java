package com.optivem.eshop.systemtest.core.dsl.shop.commands;

import com.optivem.eshop.systemtest.core.drivers.system.commons.dtos.PlaceOrderResponse;
import com.optivem.eshop.systemtest.core.dsl.commons.commands.BaseSuccessResult;
import com.optivem.eshop.systemtest.core.dsl.commons.context.DslContext;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("UnusedReturnValue")
public class PlaceOrderVerification extends BaseSuccessResult<PlaceOrderResponse> {

    public PlaceOrderVerification(PlaceOrderResponse response, DslContext context) {
        super(response, context);
    }

    public PlaceOrderVerification orderNumber(String orderNumberResultAlias) {
        var expectedOrderNumber = context.results().getAliasValue(orderNumberResultAlias);
        var actualOrderNumber = response.getOrderNumber();
        assertThat(actualOrderNumber)
                .withFailMessage("Expected order number to be '%s', but was '%s'", expectedOrderNumber, actualOrderNumber)
                .isEqualTo(expectedOrderNumber);
        return this;
    }

    public PlaceOrderVerification orderNumberStartingWith(String prefix) {
        var actualOrderNumber = response.getOrderNumber();
        assertThat(actualOrderNumber)
                .withFailMessage("Expected order number to start with '%s', but was '%s'", prefix, actualOrderNumber)
                .startsWith(prefix);
        return this;
    }
}

