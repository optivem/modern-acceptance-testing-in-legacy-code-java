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

    SystemDsl getApp() {
        return app;
    }

    ExecutionResult<TSuccessResponse, TSuccessVerification> getExecutionResult() {
        return executionResult;
    }

    public ThenSuccessBuilder<TSuccessResponse, TSuccessVerification> shouldSucceed() {
        if (executionResult == null) {
            throw new IllegalStateException("Cannot verify success: no operation was executed");
        }
        var successVerification = executionResult.getResult().shouldSucceed();
        return new ThenSuccessBuilder<>(this, successVerification);
    }

    public ThenFailureBuilder<TSuccessResponse, TSuccessVerification> shouldFail() {
        return new ThenFailureBuilder<>(this, executionResult.getResult());
    }
}
