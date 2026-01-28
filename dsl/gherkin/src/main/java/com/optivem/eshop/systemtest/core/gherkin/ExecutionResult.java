package com.optivem.eshop.systemtest.core.gherkin;

import com.optivem.commons.dsl.ResponseVerification;
import com.optivem.eshop.systemtest.core.shop.dsl.common.ShopUseCaseResult;
import lombok.Getter;

@Getter
public class ExecutionResult<TSuccessResponse, TSuccessVerification extends ResponseVerification<TSuccessResponse>> {
    private final ShopUseCaseResult<TSuccessResponse, TSuccessVerification> result;
    private String orderNumber;
    private String couponCode;

    ExecutionResult(ShopUseCaseResult<TSuccessResponse, TSuccessVerification> result, String orderNumber, String couponCode) {
        if (result == null) {
            throw new IllegalArgumentException("Result cannot be null");
        }

        this.result = result;
        this.orderNumber = orderNumber;
        this.couponCode = couponCode;
    }
}
