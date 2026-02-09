package com.optivem.eshop.systemtest.core.gherkin.then;

import com.optivem.commons.dsl.ResponseVerification;
import com.optivem.eshop.systemtest.core.SystemDsl;
import com.optivem.eshop.systemtest.core.gherkin.ExecutionResult;
import com.optivem.eshop.systemtest.core.gherkin.then.outcome.ThenFailureBuilder;
import com.optivem.eshop.systemtest.core.gherkin.then.outcome.ThenSuccessBuilder;

public class ThenClause<TSuccessResponse, TSuccessVerification extends ResponseVerification<TSuccessResponse>> {
    private final SystemDsl app;
    private final ExecutionResult<TSuccessResponse, TSuccessVerification> executionResult;

    public ThenClause(SystemDsl app, ExecutionResult<TSuccessResponse, TSuccessVerification> executionResult) {
        this.app = app;
        this.executionResult = executionResult;
    }

    public ThenSuccessBuilder<TSuccessResponse, TSuccessVerification> shouldSucceed() {
        if (executionResult == null) {
            throw new IllegalStateException("Cannot verify success: no operation was executed");
        }
        var successVerification = executionResult.getResult().shouldSucceed();
        return new ThenSuccessBuilder<>(app, executionResult.getContext(), successVerification);
    }

    public ThenFailureBuilder<TSuccessResponse, TSuccessVerification> shouldFail() {
        return new ThenFailureBuilder<>(app, executionResult.getContext(), executionResult.getResult());
    }
}
