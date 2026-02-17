package com.optivem.eshop.systemtest.core.gherkin.when;

import com.optivem.commons.dsl.VoidVerification;
import com.optivem.eshop.systemtest.core.SystemDsl;
import com.optivem.eshop.systemtest.core.gherkin.ExecutionResult;
import com.optivem.eshop.systemtest.core.gherkin.ExecutionResultBuilder;

public class GoToShopBuilder extends BaseWhenBuilder<Void, VoidVerification> {

    public GoToShopBuilder(SystemDsl app) {
        super(app);
    }

    @Override
    protected ExecutionResult<Void, VoidVerification> execute(SystemDsl app) {
        var result = app.shop().goToShop().execute();
        return new ExecutionResultBuilder<>(result).build();
    }
}
