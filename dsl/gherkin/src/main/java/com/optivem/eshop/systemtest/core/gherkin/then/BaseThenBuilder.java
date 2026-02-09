package com.optivem.eshop.systemtest.core.gherkin.then;

import com.optivem.commons.dsl.ResponseVerification;

public abstract class BaseThenBuilder<TSuccessResponse, TSuccessVerification extends ResponseVerification<TSuccessResponse>>
        extends BaseThenBuilderCore {

    protected BaseThenBuilder(ThenClauseContext thenClauseContext) {
        super(thenClauseContext);
    }

    @Override
    public BaseThenBuilder<TSuccessResponse, TSuccessVerification> and() {
        return this;
    }
}
