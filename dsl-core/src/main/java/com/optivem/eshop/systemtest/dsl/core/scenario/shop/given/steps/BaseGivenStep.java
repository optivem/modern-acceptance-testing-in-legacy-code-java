package com.optivem.eshop.systemtest.dsl.core.scenario.shop.given.steps;

import com.optivem.eshop.systemtest.dsl.core.app.AppDsl;
import com.optivem.eshop.systemtest.dsl.core.scenario.shop.given.GivenImpl;
import com.optivem.eshop.systemtest.dsl.port.shop.then.Then;
import com.optivem.eshop.systemtest.dsl.port.shop.given.steps.base.GivenStep;
import com.optivem.eshop.systemtest.dsl.core.scenario.shop.when.WhenImpl;

public abstract class BaseGivenStep implements GivenStep {
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

    public Then then() {
        return given.then();
    }

    public abstract void execute(AppDsl app);
}

