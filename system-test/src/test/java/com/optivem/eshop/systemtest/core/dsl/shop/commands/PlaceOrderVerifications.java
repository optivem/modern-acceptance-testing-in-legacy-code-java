package com.optivem.eshop.systemtest.core.dsl.shop.commands;

import com.optivem.eshop.systemtest.core.drivers.system.commons.dtos.PlaceOrderResponse;
import com.optivem.eshop.systemtest.core.dsl.commons.commands.BaseSuccessResult;
import com.optivem.eshop.systemtest.core.dsl.commons.context.DslContext;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("UnusedReturnValue")
public class PlaceOrderVerifications extends BaseSuccessResult<PlaceOrderResponse> {

    public PlaceOrderVerifications(PlaceOrderResponse response, DslContext context) {
        super(response, context);
    }

    public PlaceOrderVerifications shouldHaveOrderNumber(String orderNumberResultAlias) {
        var expectedOrderNumber = context.results().getAliasValue(orderNumberResultAlias);
        assertThat(response.getOrderNumber()).isEqualTo(expectedOrderNumber);
        return this;
    }

    public PlaceOrderVerifications shouldHaveOrderNumberStartingWith(String prefix) {
        assertThat(response.getOrderNumber()).startsWith(prefix);
        return this;
    }
}

