package com.optivem.eshop.systemtest.core.gherkin.given.steps;

import com.optivem.eshop.systemtest.core.SystemDsl;
import com.optivem.eshop.systemtest.core.gherkin.given.GivenImpl;
import com.optivem.eshop.systemtest.dsl.api.given.steps.GivenStepPort;
import com.optivem.eshop.systemtest.core.gherkin.when.WhenImpl;

public abstract class BaseGivenStep implements GivenStepPort {
    private final GivenImpl given;

    protected BaseGivenStep(GivenImpl given) {
        this.given = given;
    }

    public GivenImpl and() {
        return given;
    }

    public WhenImpl when() {
        return given.when();
    }

    public abstract void execute(SystemDsl app);
}