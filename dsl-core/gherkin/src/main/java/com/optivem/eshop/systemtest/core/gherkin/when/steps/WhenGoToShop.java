package com.optivem.eshop.systemtest.core.gherkin.when.steps;

import com.optivem.commons.dsl.VoidVerification;
import com.optivem.eshop.systemtest.core.SystemDsl;
import com.optivem.eshop.systemtest.core.gherkin.ExecutionResult;
import com.optivem.eshop.systemtest.core.gherkin.ExecutionResultBuilder;
import com.optivem.eshop.systemtest.dsl.api.when.steps.WhenStepPort;

public class WhenGoToShop extends BaseWhenStep<Void, VoidVerification> implements WhenStepPort {

    public WhenGoToShop(SystemDsl app) {
        super(app);
    }

    @Override
    protected ExecutionResult<Void, VoidVerification> execute(SystemDsl app) {
        var result = app.shop().goToShop().execute();
        return new ExecutionResultBuilder<>(result).build();
    }
}