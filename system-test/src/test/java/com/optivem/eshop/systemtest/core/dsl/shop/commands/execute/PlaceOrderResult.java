package com.optivem.eshop.systemtest.core.dsl.shop.commands.execute;

import com.optivem.eshop.systemtest.core.drivers.system.commons.dtos.PlaceOrderResponse;
import com.optivem.results.Result;

import static com.optivem.testing.assertions.ResultAssert.assertThatResult;
import static org.assertj.core.api.Assertions.assertThat;

public class PlaceOrderResult {
    private final Result<PlaceOrderResponse> result;
    private final String orderNumber;

    public PlaceOrderResult(Result<PlaceOrderResponse> result) {
        this.result = result;
        this.orderNumber = result.isSuccess() ? result.getValue().getOrderNumber() : null;
    }

    public PlaceOrderResult expectSuccess() {
        assertThatResult(result).isSuccess();
        return this;
    }

    public PlaceOrderResult expectOrderNumberStartsWith(String prefix) {
        assertThat(orderNumber).startsWith(prefix);
        return this;
    }

    public PlaceOrderResult expectOrderNumber(String expected) {
        assertThat(orderNumber).isEqualTo(expected);
        return this;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public Result<PlaceOrderResponse> getResult() {
        return result;
    }
}

