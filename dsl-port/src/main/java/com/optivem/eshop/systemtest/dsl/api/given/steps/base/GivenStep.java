package com.optivem.eshop.systemtest.dsl.api.given.steps.base;

import com.optivem.eshop.systemtest.dsl.api.given.Given;
import com.optivem.eshop.systemtest.dsl.api.when.When;

public interface GivenStep {
    Given and();

    When when();
}
