package com.optivem.eshop.systemtest.core.dsl.shop.commands.execute;

import com.optivem.eshop.systemtest.core.drivers.system.commons.dtos.PlaceOrderResponse;
import com.optivem.eshop.systemtest.core.dsl.commons.context.DslContext;
import com.optivem.eshop.systemtest.core.dsl.commons.commands.BaseSuccessResult;

import static org.assertj.core.api.Assertions.assertThat;

public class PlaceOrderSuccessResult extends BaseSuccessResult<PlaceOrderResponse> {

    public PlaceOrderSuccessResult(PlaceOrderResponse response, DslContext context) {
        super(response, context);
    }

    public PlaceOrderSuccessResult expectOrderNumber(String orderNumberResultAlias) {
        var expectedOrderNumber = context.results().getAliasValue(orderNumberResultAlias);
        assertThat(response.getOrderNumber()).isEqualTo(expectedOrderNumber);
        return this;
    }

    public PlaceOrderSuccessResult expectOrderNumberStartsWith(String prefix) {
        assertThat(response.getOrderNumber()).startsWith(prefix);
        return this;
    }
}

