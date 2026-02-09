package com.optivem.eshop.systemtest.core.gherkin.then;

import com.optivem.eshop.systemtest.core.SystemDsl;
import com.optivem.eshop.systemtest.core.gherkin.ExecutionResultContext;

public abstract class BaseThenVerificationBuilder extends BaseThenBuilderCore {
    protected BaseThenVerificationBuilder(SystemDsl app, ExecutionResultContext executionResult) {
        super(app, executionResult);
    }
}
