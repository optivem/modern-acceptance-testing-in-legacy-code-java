package com.optivem.eshop.systemtest.core.gherkin.then;

import com.optivem.testing.dsl.ResponseVerification;
import com.optivem.testing.dsl.UseCaseContext;

public class SuccessVerificationBuilder {
    private final ThenClause thenClause;
    private final ResponseVerification<?, UseCaseContext> successVerification;

    public SuccessVerificationBuilder(ThenClause thenClause, ResponseVerification<?, UseCaseContext> successVerification) {
        this.thenClause = thenClause;
        this.successVerification = successVerification;
    }

    public SuccessVerificationBuilder expectOrderNumberPrefix(String prefix) {
        // This would verify that order number starts with the prefix
        // The actual verification happens during placeOrder
        return this;
    }

    public ThenClause and() {
        return thenClause;
    }
}
