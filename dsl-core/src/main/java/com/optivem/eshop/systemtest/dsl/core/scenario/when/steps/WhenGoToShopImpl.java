package com.optivem.eshop.systemtest.dsl.core.scenario.when.steps;

import com.optivem.eshop.systemtest.dsl.core.system.shared.VoidVerification;
import com.optivem.eshop.systemtest.dsl.core.system.SystemDsl;
import com.optivem.eshop.systemtest.dsl.core.scenario.ExecutionResult;
import com.optivem.eshop.systemtest.dsl.core.scenario.ExecutionResultBuilder;
import com.optivem.eshop.systemtest.dsl.api.when.steps.base.WhenStep;

public class WhenGoToShopImpl extends BaseWhenStep<Void, VoidVerification> implements WhenStep {

    public WhenGoToShopImpl(SystemDsl app) {
        super(app);
    }

    @Override
    protected ExecutionResult<Void, VoidVerification> execute(SystemDsl app) {
        var result = app.shop().goToShop().execute();
        return new ExecutionResultBuilder<>(result).build();
    }
}

