package com.optivem.eshop.systemtest.core.gherkin.when.steps;

import com.optivem.eshop.systemtest.core.SystemDsl;
import com.optivem.eshop.systemtest.core.gherkin.ExecutionResult;
import com.optivem.eshop.systemtest.core.gherkin.ExecutionResultBuilder;
import com.optivem.eshop.systemtest.dsl.api.when.steps.WhenStepPort;
import com.optivem.eshop.systemtest.driver.api.shop.dtos.coupons.BrowseCouponsResponse;
import com.optivem.eshop.systemtest.core.shop.dsl.usecases.BrowseCouponsVerification;

public class WhenBrowseCoupons extends BaseWhenStep<BrowseCouponsResponse, BrowseCouponsVerification> implements WhenStepPort {
    public WhenBrowseCoupons(SystemDsl app) {
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