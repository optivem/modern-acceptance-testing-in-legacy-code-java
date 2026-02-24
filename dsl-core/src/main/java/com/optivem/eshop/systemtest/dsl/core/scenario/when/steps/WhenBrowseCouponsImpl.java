package com.optivem.eshop.systemtest.dsl.core.scenario.when.steps;

import com.optivem.eshop.systemtest.dsl.core.system.SystemDsl;
import com.optivem.eshop.systemtest.dsl.core.scenario.ExecutionResult;
import com.optivem.eshop.systemtest.dsl.core.scenario.ExecutionResultBuilder;
import com.optivem.eshop.systemtest.dsl.api.when.steps.WhenStep;
import com.optivem.eshop.systemtest.driver.api.shop.dtos.coupons.BrowseCouponsResponse;
import com.optivem.eshop.systemtest.dsl.core.system.shop.dsl.usecases.BrowseCouponsVerification;

public class WhenBrowseCouponsImpl extends BaseWhenStep<BrowseCouponsResponse, BrowseCouponsVerification> implements WhenStep {
    public WhenBrowseCouponsImpl(SystemDsl app) {
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

