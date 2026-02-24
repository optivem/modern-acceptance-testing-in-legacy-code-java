package com.optivem.eshop.systemtest.dsl.core.scenario;

import com.optivem.eshop.systemtest.dsl.core.system.SystemDsl;
import com.optivem.eshop.systemtest.dsl.api.ScenarioDsl;
import com.optivem.eshop.systemtest.dsl.core.scenario.given.GivenImpl;
import com.optivem.eshop.systemtest.dsl.core.scenario.when.WhenImpl;

public class ScenarioDslImpl implements ScenarioDsl {
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

