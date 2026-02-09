package com.optivem.eshop.systemtest.core.gherkin.then;

import com.optivem.commons.dsl.ResponseVerification;
import com.optivem.eshop.systemtest.core.SystemDsl;
import com.optivem.eshop.systemtest.core.gherkin.ExecutionResultContext;

public abstract class BaseThenOutcomeBuilder<TSuccessResponse, TSuccessVerification extends ResponseVerification<TSuccessResponse>>
        extends BaseThenBuilder {

    protected BaseThenOutcomeBuilder(SystemDsl app, ExecutionResultContext executionResult) {
        super(app, executionResult);
    }

    @Override
    public BaseThenOutcomeBuilder<TSuccessResponse, TSuccessVerification> and() {
        return this;
    }
}
