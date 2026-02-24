package com.optivem.eshop.systemtest.dsl.core.gherkin.then;

import com.optivem.commons.dsl.ResponseVerification;
import com.optivem.eshop.systemtest.dsl.core.system.SystemDsl;
import com.optivem.eshop.systemtest.dsl.core.gherkin.ExecutionResult;
import com.optivem.eshop.systemtest.dsl.core.gherkin.then.steps.ThenFailureImpl;
import com.optivem.eshop.systemtest.dsl.core.gherkin.then.steps.ThenSuccessImpl;
import com.optivem.eshop.systemtest.dsl.api.then.ThenPort;

public class ThenImpl<TSuccessResponse, TSuccessVerification extends ResponseVerification<TSuccessResponse>> implements ThenPort {
    private final SystemDsl app;
    private final ExecutionResult<TSuccessResponse, TSuccessVerification> executionResult;

    public ThenImpl(SystemDsl app, ExecutionResult<TSuccessResponse, TSuccessVerification> executionResult) {
        this.app = app;
        this.executionResult = executionResult;
    }

    public ThenSuccessImpl<TSuccessResponse, TSuccessVerification> shouldSucceed() {
        if (executionResult == null) {
            throw new IllegalStateException("Cannot verify success: no operation was executed");
        }
        var successVerification = executionResult.getResult().shouldSucceed();
        return new ThenSuccessImpl<>(app, executionResult.getContext(), successVerification);
    }

    public ThenFailureImpl<TSuccessResponse, TSuccessVerification> shouldFail() {
        return new ThenFailureImpl<>(app, executionResult.getContext(), executionResult.getResult());
    }
}
