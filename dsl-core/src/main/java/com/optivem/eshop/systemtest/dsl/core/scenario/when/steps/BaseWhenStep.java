package com.optivem.eshop.systemtest.dsl.core.scenario.when.steps;

import com.optivem.eshop.systemtest.dsl.core.app.ResponseVerification;
import com.optivem.eshop.systemtest.dsl.core.app.AppDsl;
import com.optivem.eshop.systemtest.dsl.core.scenario.ExecutionResult;
import com.optivem.eshop.systemtest.dsl.core.scenario.then.ThenResultImpl;

public abstract class BaseWhenStep<TSuccessResponse, TSuccessVerification extends ResponseVerification<TSuccessResponse>> {
    private final AppDsl app;

    protected BaseWhenStep(AppDsl app) {
        this.app = app;
    }
    public ThenResultImpl<TSuccessResponse, TSuccessVerification> then() {
        var result = execute(app);
        return new ThenResultImpl<>(app, result);
    }

    protected abstract ExecutionResult<TSuccessResponse, TSuccessVerification> execute(AppDsl app);
}

