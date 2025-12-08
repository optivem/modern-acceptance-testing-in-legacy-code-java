package com.optivem.eshop.systemtest.core.dsl.shop.commands.execute;

import com.optivem.eshop.systemtest.core.drivers.system.commons.dtos.PlaceOrderResponse;
import com.optivem.eshop.systemtest.core.dsl.commons.DslContext;
import com.optivem.results.Result;

import static com.optivem.testing.assertions.ResultAssert.assertThatResult;
import static org.assertj.core.api.Assertions.assertThat;

public class PlaceOrderResult {
    private final Result<PlaceOrderResponse> result;
    private final String orderNumber;
    private final DslContext context;

    public PlaceOrderResult(Result<PlaceOrderResponse> result, DslContext context) {
        this.result = result;
        this.orderNumber = result.isSuccess() ? result.getValue().getOrderNumber() : null;
        this.context = context;
    }

    public PlaceOrderResult expectSuccess() {
        assertThatResult(result).isSuccess();
        return this;
    }

    public PlaceOrderResult expectFailure() {
        assertThatResult(result).isFailure();
        return this;
    }

    public PlaceOrderResult expectErrorMessage(String expectedMessage) {
        assertThatResult(result).isFailure();

        // Replace all aliases in the expected message with their actual generated values
        var expandedExpectedMessage = expectedMessage;
        var aliases = context.params().getAllAliases();
        for (var entry : aliases.entrySet()) {
            var alias = entry.getKey();
            var actualValue = entry.getValue();
            expandedExpectedMessage = expandedExpectedMessage.replace(alias, actualValue);
        }

        var errors = result.getErrors();
        var finalExpectedMessage = expandedExpectedMessage;
        assertThat(errors)
                .withFailMessage("Expected error message: '%s', but got: %s", finalExpectedMessage, errors)
                .contains(finalExpectedMessage);
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


