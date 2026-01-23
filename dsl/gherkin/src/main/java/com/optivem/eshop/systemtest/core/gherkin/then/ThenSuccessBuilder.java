package com.optivem.eshop.systemtest.core.gherkin.then;

import com.optivem.eshop.systemtest.core.shop.dsl.orders.PlaceOrderVerification;
import com.optivem.eshop.systemtest.core.shop.dsl.orders.ViewOrderVerification;
import com.optivem.commons.dsl.ResponseVerification;
import com.optivem.commons.dsl.UseCaseContext;

public class ThenSuccessBuilder<TVerification extends ResponseVerification<?, UseCaseContext>> extends BaseThenBuilder {
    private final TVerification successVerification;

    public ThenSuccessBuilder(ThenClause thenClause, TVerification successVerification) {
        super(thenClause);
        this.successVerification = successVerification;
    }

    public ThenSuccessBuilder<TVerification> hasOrderNumberPrefix(String prefix) {
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
