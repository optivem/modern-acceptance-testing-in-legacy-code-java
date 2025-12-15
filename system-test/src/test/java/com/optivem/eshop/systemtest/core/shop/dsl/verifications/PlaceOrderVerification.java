package com.optivem.eshop.systemtest.core.shop.dsl.verifications;

import com.optivem.eshop.systemtest.core.shop.driver.dtos.responses.PlaceOrderResponse;
import com.optivem.testing.dsl.BaseUseCaseSuccessVerification;
import com.optivem.testing.dsl.UseCaseContext;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("UnusedReturnValue")
public class PlaceOrderVerification extends BaseUseCaseSuccessVerification<PlaceOrderResponse> {

    public PlaceOrderVerification(PlaceOrderResponse response, UseCaseContext context) {
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

