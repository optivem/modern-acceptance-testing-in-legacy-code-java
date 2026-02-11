package com.optivem.eshop.systemtest.core.gherkin.then;

import com.optivem.commons.dsl.VoidVerification;
import com.optivem.eshop.systemtest.core.SystemDsl;
import com.optivem.eshop.systemtest.core.gherkin.ExecutionResultContext;
import com.optivem.eshop.systemtest.core.shop.dsl.common.FailureResult;
import com.optivem.eshop.systemtest.core.shop.dsl.common.verifications.SystemErrorFailureVerification;

public class ThenFailureVerifier extends BaseThenVerifier<Void, VoidVerification> {
    private final SystemErrorFailureVerification failureVerification;

    public ThenFailureVerifier(SystemDsl app, ExecutionResultContext executionResult, FailureResult result) {
        super(app, executionResult, null);
        if (result == null) {
            throw new IllegalStateException("Cannot verify failure: no operation was executed");
        }
        this.failureVerification = result.shouldFail();
    }

    public ThenFailureVerifier errorMessage(String expectedMessage) {
        failureVerification.errorMessage(expectedMessage);
        return this;
    }

    public ThenFailureVerifier fieldErrorMessage(String expectedField, String expectedMessage) {
        failureVerification.fieldErrorMessage(expectedField, expectedMessage);
        return this;
    }
}
