package com.optivem.eshop.systemtest.core.gherkin.when;

import com.optivem.commons.dsl.ResponseVerification;
import com.optivem.eshop.systemtest.core.SystemDsl;
import com.optivem.eshop.systemtest.core.gherkin.ExecutionResult;
import com.optivem.eshop.systemtest.core.gherkin.then.Then;

public abstract class BaseWhenStep<TSuccessResponse, TSuccessVerification extends ResponseVerification<TSuccessResponse>> {
    private final SystemDsl app;

    protected BaseWhenStep(SystemDsl app) {
        this.app = app;
    }
    public Then<TSuccessResponse, TSuccessVerification> then() {
        var result = execute(app);
        return new Then<>(app, result);
    }

    protected abstract ExecutionResult<TSuccessResponse, TSuccessVerification> execute(SystemDsl app);
}