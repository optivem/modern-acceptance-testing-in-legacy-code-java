package com.optivem.eshop.systemtest.core.gherkin.then;

import com.optivem.commons.dsl.ResponseVerification;
import com.optivem.eshop.systemtest.core.shop.dsl.orders.verifications.PlaceOrderVerification;
import com.optivem.eshop.systemtest.core.shop.dsl.orders.verifications.ViewOrderVerification;

public class ThenSuccessBuilder<TSuccessResponse, TSuccessVerification extends ResponseVerification<TSuccessResponse>>
        extends BaseThenBuilder<TSuccessResponse, TSuccessVerification> {
    private final TSuccessVerification successVerification;

    public ThenSuccessBuilder(ThenClause<TSuccessResponse, TSuccessVerification> thenClause, TSuccessVerification successVerification) {
        super(thenClause);
        this.successVerification = successVerification;
    }

    public ThenSuccessBuilder<TSuccessResponse, TSuccessVerification> hasOrderNumberPrefix(String prefix) {
        switch (successVerification) {
            case PlaceOrderVerification placeOrderVerification -> placeOrderVerification.orderNumberStartsWith(prefix);
            case ViewOrderVerification viewOrderVerification -> viewOrderVerification.orderNumberHasPrefix(prefix);
            default -> throw new IllegalStateException("hasOrderNumberPrefix is not supported for this verification type");
        }
        return this;
    }
}
