package com.optivem.eshop.systemtest.dsl.core.gherkin.when.steps;

import com.optivem.commons.dsl.ResponseVerification;
import com.optivem.eshop.systemtest.dsl.core.system.SystemDsl;
import com.optivem.eshop.systemtest.dsl.core.gherkin.ExecutionResult;
import com.optivem.eshop.systemtest.dsl.core.gherkin.then.ThenImpl;

public abstract class BaseWhenStep<TSuccessResponse, TSuccessVerification extends ResponseVerification<TSuccessResponse>> {
    private final SystemDsl app;

    protected BaseWhenStep(SystemDsl app) {
        this.app = app;
    }
    public ThenImpl<TSuccessResponse, TSuccessVerification> then() {
        var result = execute(app);
        return new ThenImpl<>(app, result);
    }

    protected abstract ExecutionResult<TSuccessResponse, TSuccessVerification> execute(SystemDsl app);
}