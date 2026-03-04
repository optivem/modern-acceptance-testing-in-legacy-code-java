package com.optivem.eshop.systemtest.dsl.core.scenario.then.steps;

import com.optivem.eshop.systemtest.dsl.core.app.shared.ResponseVerification;
import com.optivem.eshop.systemtest.dsl.core.app.AppDsl;
import com.optivem.eshop.systemtest.dsl.core.scenario.ExecutionResultContext;
import com.optivem.eshop.systemtest.dsl.port.scenario.then.steps.ThenSuccess;

public class ThenSuccessImpl<TSuccessResponse, TSuccessVerification extends ResponseVerification<TSuccessResponse>>
        extends BaseThenStep<TSuccessResponse, TSuccessVerification> implements ThenSuccess {

    public ThenSuccessImpl(AppDsl app, ExecutionResultContext executionResult, TSuccessVerification successVerification) {
        super(app, executionResult, successVerification);
    }

    @Override
    public ThenSuccessImpl<TSuccessResponse, TSuccessVerification> and() {
        return this;
    }
}

