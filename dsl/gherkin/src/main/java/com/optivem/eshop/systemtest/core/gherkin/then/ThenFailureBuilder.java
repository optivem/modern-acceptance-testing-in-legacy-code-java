package com.optivem.eshop.systemtest.core.gherkin.then;

import com.optivem.commons.dsl.ResponseVerification;
import com.optivem.eshop.systemtest.core.SystemDsl;
import com.optivem.eshop.systemtest.core.gherkin.ExecutionResultContext;
import com.optivem.eshop.systemtest.core.shop.dsl.common.FailureResult;
import com.optivem.eshop.systemtest.core.shop.dsl.common.verifications.SystemErrorFailureVerification;

public class ThenFailureBuilder<TSuccessResponse, TSuccessVerification extends ResponseVerification<TSuccessResponse>>
        extends BaseThenOutcomeBuilder<TSuccessResponse, TSuccessVerification> {
    private final SystemErrorFailureVerification failureVerification;

    public ThenFailureBuilder(SystemDsl app, ExecutionResultContext executionResult, FailureResult result) {
        super(app, executionResult);
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
