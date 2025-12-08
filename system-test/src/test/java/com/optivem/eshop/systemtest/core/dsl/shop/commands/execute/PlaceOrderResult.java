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

    public FailureResult expectFailure() {
        assertThatResult(result).isFailure();
        return new FailureResult(result, context);
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
}


