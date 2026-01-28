package com.optivem.eshop.systemtest.core.gherkin.then;

import com.optivem.commons.dsl.ResponseVerification;

public abstract class BaseThenBuilder<TSuccessResponse, TSuccessVerification extends ResponseVerification<TSuccessResponse>> {
    private final ThenClause<TSuccessResponse, TSuccessVerification> thenClause;

    protected BaseThenBuilder(ThenClause<TSuccessResponse, TSuccessVerification> thenClause) {
        this.thenClause = thenClause;
    }

    public ThenClause<TSuccessResponse, TSuccessVerification> and() {
        return thenClause;
    }
}
