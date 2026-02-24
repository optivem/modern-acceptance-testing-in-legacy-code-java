package com.optivem.eshop.systemtest.core.gherkin.given.steps;

import com.optivem.eshop.systemtest.core.SystemDsl;
import com.optivem.eshop.systemtest.core.gherkin.given.Given;
import com.optivem.eshop.systemtest.dsl.api.given.steps.GivenStepPort;
import com.optivem.eshop.systemtest.core.gherkin.when.When;

public abstract class BaseGivenStep implements GivenStepPort {
    private final Given given;

    protected BaseGivenStep(Given given) {
        this.given = given;
    }

    public Given and() {
        return given;
    }

    public When when() {
        return given.when();
    }

    public abstract void execute(SystemDsl app);
}