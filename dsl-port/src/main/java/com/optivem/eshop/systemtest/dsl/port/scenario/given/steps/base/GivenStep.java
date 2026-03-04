package com.optivem.eshop.systemtest.dsl.port.scenario.given.steps.base;

import com.optivem.eshop.systemtest.dsl.port.scenario.given.Given;
import com.optivem.eshop.systemtest.dsl.port.scenario.then.Then;
import com.optivem.eshop.systemtest.dsl.port.scenario.when.When;

public interface GivenStep {
    Given and();

    When when();

    Then then();
}
