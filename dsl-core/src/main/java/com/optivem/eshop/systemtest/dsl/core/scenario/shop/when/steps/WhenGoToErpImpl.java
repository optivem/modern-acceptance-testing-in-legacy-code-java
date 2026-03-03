package com.optivem.eshop.systemtest.dsl.core.scenario.shop.when.steps;

import com.optivem.eshop.systemtest.dsl.core.app.AppDsl;
import com.optivem.eshop.systemtest.dsl.core.app.shared.VoidVerification;
import com.optivem.eshop.systemtest.dsl.core.scenario.shop.ExecutionResult;
import com.optivem.eshop.systemtest.dsl.core.scenario.shop.ExecutionResultBuilder;
import com.optivem.eshop.systemtest.dsl.port.shop.when.steps.base.WhenStep;

public class WhenGoToErpImpl extends BaseWhenStep<Void, VoidVerification> implements WhenStep {
    public WhenGoToErpImpl(AppDsl app) {
        super(app);
    }

    @Override
    protected ExecutionResult<Void, VoidVerification> execute(AppDsl app) {
        var result = app.erp().goToErp().execute();
        return new ExecutionResultBuilder<>(result).build();
    }
}
