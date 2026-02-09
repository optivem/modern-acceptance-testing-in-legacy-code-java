package com.optivem.eshop.systemtest.core.gherkin.then.state;

import com.optivem.eshop.systemtest.core.SystemDsl;
import com.optivem.eshop.systemtest.core.gherkin.ExecutionResultContext;
import com.optivem.eshop.systemtest.core.gherkin.then.BaseThenBuilderCore;

public abstract class BaseThenVerificationBuilder extends BaseThenBuilderCore {
    protected BaseThenVerificationBuilder(SystemDsl app, ExecutionResultContext executionResult) {
        super(app, executionResult);
    }
}
