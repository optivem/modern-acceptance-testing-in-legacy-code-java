package com.optivem.eshop.systemtest.core.gherkin.when;

import com.optivem.commons.dsl.ResponseVerification;
import com.optivem.eshop.systemtest.core.SystemDsl;
import com.optivem.eshop.systemtest.core.gherkin.ExecutionResult;
import com.optivem.eshop.systemtest.core.gherkin.then.ThenClause;

public abstract class BaseWhenBuilder<TSuccessResponse, TSuccessVerification extends ResponseVerification<TSuccessResponse>> {
    private final SystemDsl app;

    protected BaseWhenBuilder(SystemDsl app) {
        this.app = app;
    }
    public ThenClause<TSuccessResponse, TSuccessVerification> then() {
        var result = execute(app);
        return new ThenClause<>(app, result);
    }

    protected abstract ExecutionResult<TSuccessResponse, TSuccessVerification> execute(SystemDsl app);
}
