package com.optivem.eshop.systemtest.core.gherkin;

import com.optivem.commons.dsl.ResponseVerification;
import com.optivem.eshop.systemtest.core.system.shop.dsl.usecases.base.ShopUseCaseResult;
import lombok.Getter;

@Getter
public class ExecutionResult<TSuccessResponse, TSuccessVerification extends ResponseVerification<TSuccessResponse>> {
    private final ShopUseCaseResult<TSuccessResponse, TSuccessVerification> result;
    private final ExecutionResultContext context;

    ExecutionResult(ShopUseCaseResult<TSuccessResponse, TSuccessVerification> result, String orderNumber, String couponCode) {
        if (result == null) {
            throw new IllegalArgumentException("Result cannot be null");
        }
        this.result = result;
        this.context = new ExecutionResultContext(orderNumber, couponCode);
    }
}
