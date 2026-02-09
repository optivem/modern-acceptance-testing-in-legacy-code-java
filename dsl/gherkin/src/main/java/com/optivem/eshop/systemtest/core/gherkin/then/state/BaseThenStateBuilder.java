package com.optivem.eshop.systemtest.core.gherkin.then.state;

import com.optivem.eshop.systemtest.core.SystemDsl;
import com.optivem.eshop.systemtest.core.gherkin.ExecutionResultContext;
import com.optivem.eshop.systemtest.core.gherkin.then.BaseThenBuilder;

public abstract class BaseThenStateBuilder extends BaseThenBuilder {
    protected BaseThenStateBuilder(SystemDsl app, ExecutionResultContext executionResult) {
        super(app, executionResult);
    }
}
