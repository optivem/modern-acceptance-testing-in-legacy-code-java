package com.optivem.eshop.systemtest.core.gherkin;

import com.optivem.eshop.systemtest.core.shop.dsl.common.ShopUseCaseResult;
import lombok.Getter;

@Getter
public class ExecutionResult {
    private final ShopUseCaseResult<?, ?> result;
    private String orderNumber;
    private String couponCode;

    private ExecutionResult(ShopUseCaseResult<?, ?> result) {
        if (result == null) {
            throw new IllegalArgumentException("Result cannot be null");
        }
        this.result = result;
    }

    public static ExecutionResultBuilder builder(ShopUseCaseResult<?, ?> result) {
        return new ExecutionResultBuilder(result);
    }

    public static class ExecutionResultBuilder {
        private final ExecutionResult executionResult;

        private ExecutionResultBuilder(ShopUseCaseResult<?, ?> result) {
            this.executionResult = new ExecutionResult(result);
        }

        public ExecutionResultBuilder orderNumber(String orderNumber) {
            executionResult.orderNumber = orderNumber;
            return this;
        }

        public ExecutionResultBuilder couponCode(String couponCode) {
            executionResult.couponCode = couponCode;
            return this;
        }

        public ExecutionResult build() {
            return executionResult;
        }
    }
}
