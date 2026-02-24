package com.optivem.eshop.systemtest.core.gherkin;

import com.optivem.eshop.systemtest.core.SystemDsl;
import com.optivem.eshop.systemtest.dsl.api.ScenarioDslPort;
import com.optivem.eshop.systemtest.core.gherkin.given.Given;
import com.optivem.eshop.systemtest.core.gherkin.when.When;

public class ScenarioDsl implements ScenarioDslPort {
    private final SystemDsl app;
    private boolean executed = false;

    public ScenarioDsl(SystemDsl app) {
        this.app = app;
    }

    public Given given() {
        ensureNotExecuted();
        return new Given(app);
    }

    public When when() {
        ensureNotExecuted();
        return new When(app);
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
}
