package com.optivem.eshop.systemtest.core.gherkin.then;

import com.optivem.eshop.systemtest.core.SystemDsl;
import com.optivem.eshop.systemtest.core.gherkin.ExecutionResultContext;

public abstract class BaseThenStateBuilder extends BaseThenBuilder {
    protected BaseThenStateBuilder(SystemDsl app, ExecutionResultContext executionResult) {
        super(app, executionResult);
    }
}
