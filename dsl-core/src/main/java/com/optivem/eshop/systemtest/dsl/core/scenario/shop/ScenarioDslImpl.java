package com.optivem.eshop.systemtest.dsl.core.scenario.shop;

import com.optivem.eshop.systemtest.dsl.core.app.AppDsl;
import com.optivem.eshop.systemtest.dsl.port.shop.ScenarioDsl;
import com.optivem.eshop.systemtest.dsl.core.scenario.shop.given.GivenImpl;
import com.optivem.eshop.systemtest.dsl.core.scenario.shop.when.WhenImpl;

public class ScenarioDslImpl implements ScenarioDsl {
    private final AppDsl app;
    private boolean executed = false;

    public ScenarioDslImpl(AppDsl app) {
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
                    "Each test method should contain only ONE scenario execution (Given-When-Then). " +
                    "Split multiple scenarios into separate test methods.");
        }
    }
}

