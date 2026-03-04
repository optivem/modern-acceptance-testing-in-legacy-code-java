package com.optivem.eshop.systemtest.dsl.core.scenario.when.steps;

import com.optivem.eshop.systemtest.dsl.core.app.AppDsl;
import com.optivem.eshop.systemtest.dsl.core.scenario.ExecutionResult;
import com.optivem.eshop.systemtest.dsl.core.scenario.ExecutionResultBuilder;
import com.optivem.eshop.systemtest.dsl.port.scenario.when.steps.base.WhenStep;
import com.optivem.eshop.systemtest.driver.port.shop.dtos.BrowseCouponsResponse;
import com.optivem.eshop.systemtest.dsl.core.app.shop.usecases.BrowseCouponsVerification;

public class WhenBrowseCouponsImpl extends BaseWhenStep<BrowseCouponsResponse, BrowseCouponsVerification> implements WhenStep {
    public WhenBrowseCouponsImpl(AppDsl app) {
        super(app);
    }

    @Override
    protected ExecutionResult<BrowseCouponsResponse, BrowseCouponsVerification> execute(AppDsl app) {
        var result = app.shop()
                .browseCoupons()
                .execute();

        return new ExecutionResultBuilder<>(result)
                .build();
    }
}

