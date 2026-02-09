package com.optivem.eshop.systemtest.core.gherkin.then;

import com.optivem.commons.dsl.ResponseVerification;
import com.optivem.eshop.systemtest.core.SystemDsl;
import com.optivem.eshop.systemtest.core.gherkin.ExecutionResultContext;
import com.optivem.eshop.systemtest.core.shop.dsl.orders.verifications.PlaceOrderVerification;
import com.optivem.eshop.systemtest.core.shop.dsl.orders.verifications.ViewOrderVerification;

public class ThenSuccessVerifier<TSuccessResponse, TSuccessVerification extends ResponseVerification<TSuccessResponse>>
        extends BaseThenOutcomeVerifier<TSuccessResponse, TSuccessVerification> {
    private final TSuccessVerification successVerification;

    public ThenSuccessVerifier(SystemDsl app, ExecutionResultContext executionResult, TSuccessVerification successVerification) {
        super(app, executionResult);
        this.successVerification = successVerification;
    }

    public ThenSuccessVerifier<TSuccessResponse, TSuccessVerification> hasOrderNumberPrefix(String prefix) {
        switch (successVerification) {
            case PlaceOrderVerification placeOrderVerification -> placeOrderVerification.orderNumberStartsWith(prefix);
            case ViewOrderVerification viewOrderVerification -> viewOrderVerification.orderNumberHasPrefix(prefix);
            default -> throw new IllegalStateException("hasOrderNumberPrefix is not supported for this verification type");
        }
        return this;
    }
}
