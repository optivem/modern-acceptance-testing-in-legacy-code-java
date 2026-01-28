package com.optivem.eshop.systemtest.core.gherkin.then;

import com.optivem.commons.dsl.ResponseVerification;
import com.optivem.eshop.systemtest.core.shop.dsl.common.SystemErrorFailureVerification;
import com.optivem.eshop.systemtest.core.shop.dsl.common.ShopUseCaseResult;

public class ThenFailureBuilder<TSuccessResponse, TSuccessVerification extends ResponseVerification<TSuccessResponse>>
        extends BaseThenBuilder<TSuccessResponse, TSuccessVerification> {
    private final SystemErrorFailureVerification failureVerification;

    public ThenFailureBuilder(ThenClause<TSuccessResponse, TSuccessVerification> thenClause, ShopUseCaseResult<?, ?> result) {
        super(thenClause);
        if (result == null) {
            throw new IllegalStateException("Cannot verify failure: no operation was executed");
        }
        this.failureVerification = result.shouldFail();
    }

    public ThenFailureBuilder<TSuccessResponse, TSuccessVerification> errorMessage(String expectedMessage) {
        failureVerification.errorMessage(expectedMessage);
        return this;
    }

    public ThenFailureBuilder<TSuccessResponse, TSuccessVerification> fieldErrorMessage(String expectedField, String expectedMessage) {
        failureVerification.fieldErrorMessage(expectedField, expectedMessage);
        return this;
    }
}
