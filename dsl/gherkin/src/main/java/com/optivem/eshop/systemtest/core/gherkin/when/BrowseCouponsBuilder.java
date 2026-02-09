package com.optivem.eshop.systemtest.core.gherkin.when;

import com.optivem.eshop.systemtest.core.SystemDsl;
import com.optivem.eshop.systemtest.core.gherkin.ExecutionResult;
import com.optivem.eshop.systemtest.core.gherkin.ExecutionResultBuilder;
import com.optivem.eshop.systemtest.core.shop.commons.dtos.coupons.BrowseCouponsResponse;
import com.optivem.eshop.systemtest.core.shop.dsl.coupons.verifications.BrowseCouponsVerification;

public class BrowseCouponsBuilder extends BaseWhenBuilder<BrowseCouponsResponse, BrowseCouponsVerification> {
    public BrowseCouponsBuilder(SystemDsl app) {
        super(app);
    }

    @Override
    protected ExecutionResult<BrowseCouponsResponse, BrowseCouponsVerification> execute(SystemDsl app) {
        var result = app.shop()
                .browseCoupons()
                .execute();

        return new ExecutionResultBuilder<>(result)
                .build();
    }
}
