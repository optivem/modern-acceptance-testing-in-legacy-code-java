package com.optivem.eshop.systemtest.core.gherkin.then;

import com.optivem.commons.dsl.ResponseVerification;
import com.optivem.eshop.systemtest.core.SystemDsl;
import com.optivem.eshop.systemtest.core.gherkin.ExecutionResult;

public class ThenClause<TSuccessResponse, TSuccessVerification extends ResponseVerification<TSuccessResponse>> {
    private final SystemDsl app;
    private final ExecutionResult<TSuccessResponse, TSuccessVerification> executionResult;

    public ThenClause(SystemDsl app, ExecutionResult<TSuccessResponse, TSuccessVerification> executionResult) {
        this.app = app;
        this.executionResult = executionResult;
    }

    public ThenSuccessVerifier<TSuccessResponse, TSuccessVerification> shouldSucceed() {
        if (executionResult == null) {
            throw new IllegalStateException("Cannot verify success: no operation was executed");
        }
        var successVerification = executionResult.getResult().shouldSucceed();
        return new ThenSuccessVerifier<>(app, executionResult.getContext(), successVerification);
    }

    public ThenFailureVerifier<TSuccessResponse, TSuccessVerification> shouldFail() {
        return new ThenFailureVerifier<>(app, executionResult.getContext(), executionResult.getResult());
    }
}
