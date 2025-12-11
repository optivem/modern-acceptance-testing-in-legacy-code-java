package com.optivem.eshop.systemtest.dsl.shop.verifications;

import com.optivem.eshop.systemtest.dsl.shop.dtos.PlaceOrderResponse;
import com.optivem.testing.dsl.BaseSuccessVerification;
import com.optivem.testing.dsl.Context;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("UnusedReturnValue")
public class PlaceOrderVerification extends BaseSuccessVerification<PlaceOrderResponse> {

    public PlaceOrderVerification(PlaceOrderResponse response, Context context) {
        super(response, context);
    }

    public PlaceOrderVerification orderNumber(String orderNumberResultAlias) {
        var expectedOrderNumber = context.getResultValue(orderNumberResultAlias);
        var actualOrderNumber = response.getOrderNumber();
        assertThat(actualOrderNumber)
                .withFailMessage("Expected order number to be '%s', but was '%s'", expectedOrderNumber, actualOrderNumber)
                .isEqualTo(expectedOrderNumber);
        return this;
    }

    public PlaceOrderVerification orderNumberStartsWith(String prefix) {
        var actualOrderNumber = response.getOrderNumber();
        assertThat(actualOrderNumber)
                .withFailMessage("Expected order number to start with '%s', but was '%s'", prefix, actualOrderNumber)
                .startsWith(prefix);
        return this;
    }
}

