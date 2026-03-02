package com.optivem.eshop.systemtest.dsl.port.given.steps.base;

import com.optivem.eshop.systemtest.dsl.port.given.Given;
import com.optivem.eshop.systemtest.dsl.port.when.When;

public interface GivenStep {
    Given and();

    When when();
}
