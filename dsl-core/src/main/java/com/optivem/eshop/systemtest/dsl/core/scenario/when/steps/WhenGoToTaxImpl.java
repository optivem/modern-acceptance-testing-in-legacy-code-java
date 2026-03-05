package com.optivem.eshop.systemtest.dsl.core.scenario.when.steps;

import com.optivem.eshop.systemtest.dsl.core.app.AppDsl;
import com.optivem.eshop.systemtest.dsl.core.shared.VoidVerification;
import com.optivem.eshop.systemtest.dsl.core.scenario.ExecutionResult;
import com.optivem.eshop.systemtest.dsl.core.scenario.ExecutionResultBuilder;
import com.optivem.eshop.systemtest.dsl.port.when.steps.base.WhenStep;

public class WhenGoToTaxImpl extends BaseWhenStep<Void, VoidVerification> implements WhenStep {
    public WhenGoToTaxImpl(AppDsl app) {
        super(app);
    }

    @Override
    protected ExecutionResult<Void, VoidVerification> execute(AppDsl app) {
        var result = app.tax().goToTax().execute();
        return new ExecutionResultBuilder<>(result).build();
    }
}



