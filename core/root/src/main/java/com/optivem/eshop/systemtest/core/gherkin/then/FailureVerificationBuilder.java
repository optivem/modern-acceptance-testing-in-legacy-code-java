package com.optivem.eshop.systemtest.core.gherkin.then;

import com.optivem.eshop.systemtest.core.SystemDsl;
import com.optivem.eshop.systemtest.core.shop.dsl.commands.base.ErrorFailureVerification;
import com.optivem.eshop.systemtest.core.shop.dsl.commands.base.ShopUseCaseResult;

public class FailureVerificationBuilder {
    private final SystemDsl app;
    private final String orderNumber;
    private final ErrorFailureVerification failureVerification;

    public FailureVerificationBuilder(SystemDsl app, String orderNumber, ShopUseCaseResult<?, ?> result) {
        this.app = app;
        this.orderNumber = orderNumber;
        this.failureVerification = result != null ? result.shouldFail() : null;
    }

    public FailureVerificationBuilder errorMessage(String expectedMessage) {
        if (failureVerification != null) {
            failureVerification.errorMessage(expectedMessage);
        }
        return this;
    }

    public FailureVerificationBuilder fieldErrorMessage(String expectedField, String expectedMessage) {
        if (failureVerification != null) {
            failureVerification.fieldErrorMessage(expectedField, expectedMessage);
        }
        return this;
    }
}
