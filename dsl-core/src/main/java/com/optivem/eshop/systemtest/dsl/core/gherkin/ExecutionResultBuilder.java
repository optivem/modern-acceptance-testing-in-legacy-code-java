package com.optivem.eshop.systemtest.dsl.core.gherkin;

import com.optivem.commons.dsl.ResponseVerification;
import com.optivem.eshop.systemtest.dsl.core.system.shop.dsl.usecases.base.ShopUseCaseResult;

public class ExecutionResultBuilder<TSuccessResponse, TSuccessVerification extends ResponseVerification<TSuccessResponse>> {

    private final ShopUseCaseResult<TSuccessResponse, TSuccessVerification> result;
    private String orderNumber;
    private String couponCode;

    public ExecutionResultBuilder(ShopUseCaseResult<TSuccessResponse, TSuccessVerification> result) {
        this.result = result;
    }

    public ExecutionResultBuilder<TSuccessResponse, TSuccessVerification> orderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
        return this;
    }

    public ExecutionResultBuilder<TSuccessResponse, TSuccessVerification> couponCode(String couponCode) {
        this.couponCode = couponCode;
        return this;
    }

    public ExecutionResult<TSuccessResponse, TSuccessVerification> build() {
        return new ExecutionResult<>(result, orderNumber, couponCode);
    }
}
