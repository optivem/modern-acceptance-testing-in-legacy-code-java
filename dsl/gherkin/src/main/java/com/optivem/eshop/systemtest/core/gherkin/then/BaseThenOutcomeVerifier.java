package com.optivem.eshop.systemtest.core.gherkin.then;

import com.optivem.commons.dsl.ResponseVerification;
import com.optivem.eshop.systemtest.core.SystemDsl;
import com.optivem.eshop.systemtest.core.gherkin.ExecutionResultContext;

public abstract class BaseThenOutcomeVerifier<TSuccessResponse, TSuccessVerification extends ResponseVerification<TSuccessResponse>>
        extends BaseThenVerifier {

    protected BaseThenOutcomeVerifier(SystemDsl app, ExecutionResultContext executionResult) {
        super(app, executionResult);
    }

    @Override
    public BaseThenOutcomeVerifier<TSuccessResponse, TSuccessVerification> and() {
        return this;
    }
}
