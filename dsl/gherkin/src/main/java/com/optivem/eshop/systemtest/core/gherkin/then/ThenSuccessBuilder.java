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
        if (successVerification instanceof PlaceOrderVerification verification) {
            verification.orderNumberStartsWith(prefix);
        } else if(successVerification instanceof ViewOrderVerification verification) {
            verification.orderNumberHasPrefix(prefix);
        } else {
            throw new IllegalStateException("hasOrderNumberPrefix is not supported for this verification type");
        }

        return this;
    }
}
