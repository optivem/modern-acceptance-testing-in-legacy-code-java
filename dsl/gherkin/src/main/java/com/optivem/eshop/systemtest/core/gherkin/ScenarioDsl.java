package com.optivem.eshop.systemtest.core.gherkin;

import com.optivem.eshop.systemtest.core.SystemDsl;
import com.optivem.eshop.systemtest.core.gherkin.given.GivenClause;
import com.optivem.eshop.systemtest.core.gherkin.when.WhenClause;
import com.optivem.commons.util.Closer;

public class ScenarioDsl implements AutoCloseable {
    private final SystemDsl app;
    private boolean executed = false;

    public ScenarioDsl(SystemDsl app) {
        this.app = app;
    }

    public GivenClause given() {
        ensureNotExecuted();
        return new GivenClause(app, this);
    }

    public WhenClause when() {
        ensureNotExecuted();
        return new WhenClause(app, this);
    }

    public void markAsExecuted() {
        this.executed = true;
    }

    private void ensureNotExecuted() {
        if (executed) {
            throw new IllegalStateException("Scenario has already been executed. " +
                    "Each test method should contain only ONE scenario execution (Given-When-Then). " +
                    "Split multiple scenarios into separate test methods.");
        }
    }

    @Override
    public void close() throws Exception {
        Closer.close(app);
    }
}
