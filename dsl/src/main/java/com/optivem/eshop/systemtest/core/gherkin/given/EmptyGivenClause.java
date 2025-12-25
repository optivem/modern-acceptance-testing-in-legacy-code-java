package com.optivem.eshop.systemtest.core.gherkin.given;

import com.optivem.eshop.systemtest.core.SystemDsl;
import com.optivem.eshop.systemtest.core.gherkin.when.WhenClause;

public class EmptyGivenClause {
    private final SystemDsl app;

    public EmptyGivenClause(SystemDsl app) {
        this.app = app;
    }

    public WhenClause when() {
        return new WhenClause(app);
    }
}
