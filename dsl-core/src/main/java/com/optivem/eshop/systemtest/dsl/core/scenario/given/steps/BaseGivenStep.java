package com.optivem.eshop.systemtest.dsl.core.scenario.given.steps;

import com.optivem.eshop.systemtest.dsl.core.system.SystemDsl;
import com.optivem.eshop.systemtest.dsl.core.scenario.given.GivenImpl;
import com.optivem.eshop.systemtest.dsl.api.given.steps.GivenStepPort;
import com.optivem.eshop.systemtest.dsl.core.scenario.when.WhenImpl;

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