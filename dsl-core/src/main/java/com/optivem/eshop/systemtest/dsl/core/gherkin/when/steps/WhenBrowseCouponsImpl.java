package com.optivem.eshop.systemtest.dsl.core.gherkin.when.steps;

import com.optivem.eshop.systemtest.dsl.core.system.SystemDsl;
import com.optivem.eshop.systemtest.dsl.core.gherkin.ExecutionResult;
import com.optivem.eshop.systemtest.dsl.core.gherkin.ExecutionResultBuilder;
import com.optivem.eshop.systemtest.dsl.api.when.steps.WhenStepPort;
import com.optivem.eshop.systemtest.driver.api.shop.dtos.coupons.BrowseCouponsResponse;
import com.optivem.eshop.systemtest.dsl.core.system.shop.dsl.usecases.BrowseCouponsVerification;

public class WhenBrowseCouponsImpl extends BaseWhenStep<BrowseCouponsResponse, BrowseCouponsVerification> implements WhenStepPort {
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