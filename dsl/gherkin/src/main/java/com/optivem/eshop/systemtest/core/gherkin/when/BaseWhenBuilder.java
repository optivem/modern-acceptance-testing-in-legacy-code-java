package com.optivem.eshop.systemtest.core.gherkin.when;

import com.optivem.commons.dsl.ResponseVerification;
import com.optivem.eshop.systemtest.core.SystemDsl;
import com.optivem.eshop.systemtest.core.gherkin.ExecutionResult;
import com.optivem.eshop.systemtest.core.gherkin.ScenarioDsl;
import com.optivem.eshop.systemtest.core.gherkin.then.ThenClause;

public abstract class BaseWhenBuilder<TSuccessResponse, TSuccessVerification extends ResponseVerification<TSuccessResponse>> {
    private final SystemDsl app;
    private final ScenarioDsl scenario;

    public BaseWhenBuilder(SystemDsl app, ScenarioDsl scenario) {
        this.app = app;
        this.scenario = scenario;
    }
    public ThenClause<TSuccessResponse, TSuccessVerification> then() {
        var result = execute(app);
        return new ThenClause<>(app, scenario, result);
    }

    protected abstract ExecutionResult<TSuccessResponse, TSuccessVerification> execute(SystemDsl app);
}
