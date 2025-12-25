package com.optivem.eshop.systemtest.core.gherkin;

import com.optivem.eshop.systemtest.core.SystemDsl;
import com.optivem.eshop.systemtest.core.gherkin.given.GivenClause;
import com.optivem.eshop.systemtest.core.gherkin.when.WhenClause;

public class ScenarioDsl {
    private final SystemDsl app;

    public ScenarioDsl(SystemDsl app) {
        this.app = app;
    }

    public GivenClause given() {
        return new GivenClause(app);
    }

    public WhenClause when() {
        return new WhenClause(app);
    }
}
