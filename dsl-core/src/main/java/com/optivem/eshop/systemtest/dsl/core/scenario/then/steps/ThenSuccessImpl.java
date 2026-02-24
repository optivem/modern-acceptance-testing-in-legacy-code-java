package com.optivem.eshop.systemtest.dsl.core.scenario.then.steps;

import com.optivem.commons.dsl.ResponseVerification;
import com.optivem.eshop.systemtest.dsl.core.system.SystemDsl;
import com.optivem.eshop.systemtest.dsl.core.scenario.ExecutionResultContext;
import com.optivem.eshop.systemtest.dsl.api.then.steps.ThenSuccessPort;

public class ThenSuccessImpl<TSuccessResponse, TSuccessVerification extends ResponseVerification<TSuccessResponse>>
        extends BaseThenStep<TSuccessResponse, TSuccessVerification> implements ThenSuccessPort {

    public ThenSuccessImpl(SystemDsl app, ExecutionResultContext executionResult, TSuccessVerification successVerification) {
        super(app, executionResult, successVerification);
    }

    @Override
    public ThenSuccessImpl<TSuccessResponse, TSuccessVerification> and() {
        return this;
    }
}
