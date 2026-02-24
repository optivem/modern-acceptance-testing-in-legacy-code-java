package com.optivem.eshop.systemtest.core.gherkin.given;

import com.optivem.eshop.systemtest.core.SystemDsl;
import com.optivem.eshop.systemtest.core.gherkin.when.When;

public abstract class BaseGivenStep {
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

    abstract void execute(SystemDsl app);
}