package com.optivem.eshop.systemtest.core.gherkin.then;

import com.optivem.commons.dsl.ResponseVerification;
import com.optivem.eshop.systemtest.core.SystemDsl;
import com.optivem.eshop.systemtest.core.gherkin.ExecutionResult;
import com.optivem.eshop.systemtest.core.gherkin.then.steps.ThenFailure;
import com.optivem.eshop.systemtest.core.gherkin.then.steps.ThenSuccess;
import com.optivem.eshop.systemtest.dsl.api.then.ThenPort;

public class Then<TSuccessResponse, TSuccessVerification extends ResponseVerification<TSuccessResponse>> implements ThenPort {
    private final SystemDsl app;
    private final ExecutionResult<TSuccessResponse, TSuccessVerification> executionResult;

    public Then(SystemDsl app, ExecutionResult<TSuccessResponse, TSuccessVerification> executionResult) {
        this.app = app;
        this.executionResult = executionResult;
    }

    public ThenSuccess<TSuccessResponse, TSuccessVerification> shouldSucceed() {
        if (executionResult == null) {
            throw new IllegalStateException("Cannot verify success: no operation was executed");
        }
        var successVerification = executionResult.getResult().shouldSucceed();
        return new ThenSuccess<>(app, executionResult.getContext(), successVerification);
    }

    public ThenFailure<TSuccessResponse, TSuccessVerification> shouldFail() {
        return new ThenFailure<>(app, executionResult.getContext(), executionResult.getResult());
    }
}
