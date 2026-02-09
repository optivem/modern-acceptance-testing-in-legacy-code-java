package com.optivem.eshop.systemtest.core.gherkin.then;

import com.optivem.commons.dsl.ResponseVerification;
import com.optivem.eshop.systemtest.core.SystemDsl;
import com.optivem.eshop.systemtest.core.gherkin.ExecutionResultContext;

public abstract class BaseThenBuilder<TSuccessResponse, TSuccessVerification extends ResponseVerification<TSuccessResponse>>
        extends BaseThenBuilderCore {

    protected BaseThenBuilder(SystemDsl app, ExecutionResultContext executionResult) {
        super(app, executionResult);
    }

    @Override
    public BaseThenBuilder<TSuccessResponse, TSuccessVerification> and() {
        return this;
    }
}
