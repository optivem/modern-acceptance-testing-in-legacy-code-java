package com.optivem.eshop.systemtest.core.gherkin.then;

import com.optivem.eshop.systemtest.core.SystemDsl;
import com.optivem.eshop.systemtest.core.shop.dsl.commands.base.ShopUseCaseResult;

public class ThenClause {
    private final SystemDsl app;
    private final String orderNumber;
    private final ShopUseCaseResult<?, ?> result;

    public ThenClause(SystemDsl app, String orderNumber) {
        this(app, orderNumber, null);
    }

    public ThenClause(SystemDsl app, String orderNumber, ShopUseCaseResult<?, ?> result) {
        this.app = app;
        this.orderNumber = orderNumber;
        this.result = result;
    }

    public SuccessVerificationBuilder shouldSucceed() {
        if (result != null) {
            result.shouldSucceed();
        }
        return new SuccessVerificationBuilder(app, orderNumber);
    }

    public FailureVerificationBuilder shouldFail() {
        return new FailureVerificationBuilder(app, orderNumber, result);
    }
}
