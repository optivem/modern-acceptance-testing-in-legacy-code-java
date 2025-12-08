package com.optivem.eshop.systemtest.core.dsl.shop.commands.execute;

import com.optivem.eshop.systemtest.core.drivers.system.commons.dtos.PlaceOrderResponse;
import com.optivem.eshop.systemtest.core.dsl.commons.DslContext;
import com.optivem.results.Result;

import static com.optivem.testing.assertions.ResultAssert.assertThatResult;
import static org.assertj.core.api.Assertions.assertThat;

public class PlaceOrderResult {
    private final Result<PlaceOrderResponse> result;
    private final DslContext context;

    public PlaceOrderResult(Result<PlaceOrderResponse> result, DslContext context) {
        this.result = result;
        this.context = context;
    }

    public PlaceOrderSuccessResult expectSuccess() {
        assertThatResult(result).isSuccess();
        return new PlaceOrderSuccessResult(result.getValue(), context);
    }

    public PlaceOrderFailureResult expectFailure() {
        assertThatResult(result).isFailure();
        return new PlaceOrderFailureResult(result, context);
    }

    public static class PlaceOrderSuccessResult {
        private final PlaceOrderResponse response;
        private final DslContext context;

        private PlaceOrderSuccessResult(PlaceOrderResponse response, DslContext context) {
            this.response = response;
            this.context = context;
        }

        public PlaceOrderSuccessResult expectOrderNumberStartsWith(String prefix) {
            assertThat(response.getOrderNumber()).startsWith(prefix);
            return this;
        }

        public PlaceOrderSuccessResult expectOrderNumber(String expected) {
            assertThat(response.getOrderNumber()).isEqualTo(expected);
            return this;
        }
    }

    public static class PlaceOrderFailureResult {
        private final Result<PlaceOrderResponse> result;
        private final DslContext context;

        private PlaceOrderFailureResult(Result<PlaceOrderResponse> result, DslContext context) {
            this.result = result;
            this.context = context;
        }

        public PlaceOrderFailureResult expectErrorMessage(String expectedMessage) {
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
    }
}


