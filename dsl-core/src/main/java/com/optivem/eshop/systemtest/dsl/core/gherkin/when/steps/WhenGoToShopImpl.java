package com.optivem.eshop.systemtest.dsl.core.gherkin.when.steps;

import com.optivem.commons.dsl.VoidVerification;
import com.optivem.eshop.systemtest.dsl.core.system.SystemDsl;
import com.optivem.eshop.systemtest.dsl.core.gherkin.ExecutionResult;
import com.optivem.eshop.systemtest.dsl.core.gherkin.ExecutionResultBuilder;
import com.optivem.eshop.systemtest.dsl.api.when.steps.WhenStepPort;

public class WhenGoToShopImpl extends BaseWhenStep<Void, VoidVerification> implements WhenStepPort {

    public WhenGoToShopImpl(SystemDsl app) {
        super(app);
    }

    @Override
    protected ExecutionResult<Void, VoidVerification> execute(SystemDsl app) {
        var result = app.shop().goToShop().execute();
        return new ExecutionResultBuilder<>(result).build();
    }
}