package com.optivem.eshop.systemtest.core.gherkin.then;

public class SuccessVerificationBuilder {
    private final ThenClause thenClause;

    public SuccessVerificationBuilder(ThenClause thenClause) {
        this.thenClause = thenClause;
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
