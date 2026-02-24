package com.optivem.eshop.systemtest.dsl.core.gherkin;

import com.optivem.eshop.systemtest.dsl.core.system.SystemDsl;
import com.optivem.eshop.systemtest.dsl.api.ScenarioDslPort;
import com.optivem.eshop.systemtest.dsl.core.gherkin.given.GivenImpl;
import com.optivem.eshop.systemtest.dsl.core.gherkin.when.WhenImpl;

public class ScenarioDslImpl implements ScenarioDslPort {
    private final SystemDsl app;
    private boolean executed = false;

    public ScenarioDslImpl(SystemDsl app) {
        this.app = app;
    }

    public GivenImpl given() {
        ensureNotExecuted();
        return new GivenImpl(app);
    }

    public WhenImpl when() {
        ensureNotExecuted();
        return new WhenImpl(app);
    }

    public void markAsExecuted() {
        this.executed = true;
    }

    private void ensureNotExecuted() {
        if (executed) {
            throw new IllegalStateException("Scenario has already been executed. " +
                    "Each test method should contain only ONE scenario execution (GivenImpl-WhenImpl-ThenImpl). " +
                    "Split multiple scenarios into separate test methods.");
        }
    }
}
