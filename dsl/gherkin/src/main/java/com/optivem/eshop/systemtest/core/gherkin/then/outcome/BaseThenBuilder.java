package com.optivem.eshop.systemtest.core.gherkin.then.outcome;

import com.optivem.commons.dsl.ResponseVerification;
import com.optivem.eshop.systemtest.core.SystemDsl;
import com.optivem.eshop.systemtest.core.gherkin.ExecutionResultContext;
import com.optivem.eshop.systemtest.core.gherkin.then.BaseThenBuilderCore;

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
