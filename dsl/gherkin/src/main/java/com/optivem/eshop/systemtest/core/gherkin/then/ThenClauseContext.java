package com.optivem.eshop.systemtest.core.gherkin.then;

import com.optivem.eshop.systemtest.core.SystemDsl;
import com.optivem.eshop.systemtest.core.gherkin.ExecutionResultContext;

public interface ThenClauseContext {
    SystemDsl getApp();

    ExecutionResultContext getExecutionResult();
}
