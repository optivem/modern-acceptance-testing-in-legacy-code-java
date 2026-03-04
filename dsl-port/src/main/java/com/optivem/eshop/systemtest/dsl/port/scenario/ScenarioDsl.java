package com.optivem.eshop.systemtest.dsl.port.scenario;

import com.optivem.eshop.systemtest.dsl.port.scenario.given.Given;
import com.optivem.eshop.systemtest.dsl.port.scenario.when.When;

public interface ScenarioDsl {
    Given given();

    When when();
}
