package com.optivem.eshop.systemtest.core.gherkin.then;

import com.optivem.eshop.systemtest.core.shop.dsl.commands.base.ErrorFailureVerification;
import com.optivem.eshop.systemtest.core.shop.dsl.commands.base.ShopUseCaseResult;

public class FailureVerificationBuilder {
    private final ThenClause thenClause;
    private final ErrorFailureVerification failureVerification;

    public FailureVerificationBuilder(ThenClause thenClause, ShopUseCaseResult<?, ?> result) {
        if (result == null) {
            throw new IllegalStateException("Cannot verify failure: no operation was executed");
        }
        this.thenClause = thenClause;
        this.failureVerification = result.shouldFail();
    }

    public FailureVerificationBuilder errorMessage(String expectedMessage) {
        failureVerification.errorMessage(expectedMessage);
        return this;
    }

    public FailureVerificationBuilder fieldErrorMessage(String expectedField, String expectedMessage) {
        failureVerification.fieldErrorMessage(expectedField, expectedMessage);
        return this;
    }

    public ThenClause and() {
        return thenClause;
    }
}
