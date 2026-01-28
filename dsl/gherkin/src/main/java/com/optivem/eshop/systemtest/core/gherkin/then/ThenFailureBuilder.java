package com.optivem.eshop.systemtest.core.gherkin.then;

import com.optivem.eshop.systemtest.core.shop.dsl.common.SystemErrorFailureVerification;
import com.optivem.eshop.systemtest.core.shop.dsl.common.ShopUseCaseResult;

public class ThenFailureBuilder extends BaseThenBuilder {
    private final SystemErrorFailureVerification failureVerification;

    public ThenFailureBuilder(ThenClause thenClause, ShopUseCaseResult<?, ?> result) {
        super(thenClause);
        if (result == null) {
            throw new IllegalStateException("Cannot verify failure: no operation was executed");
        }
        this.failureVerification = result.shouldFail();
    }

    public ThenFailureBuilder errorMessage(String expectedMessage) {
        failureVerification.errorMessage(expectedMessage);
        return this;
    }

    public ThenFailureBuilder fieldErrorMessage(String expectedField, String expectedMessage) {
        failureVerification.fieldErrorMessage(expectedField, expectedMessage);
        return this;
    }
}
