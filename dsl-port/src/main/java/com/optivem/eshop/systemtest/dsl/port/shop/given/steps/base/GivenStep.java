package com.optivem.eshop.systemtest.dsl.port.shop.given.steps.base;

import com.optivem.eshop.systemtest.dsl.port.shop.given.Given;
import com.optivem.eshop.systemtest.dsl.port.shop.then.Then;
import com.optivem.eshop.systemtest.dsl.port.shop.when.When;

public interface GivenStep {
    Given and();

    When when();

    Then then();
}
