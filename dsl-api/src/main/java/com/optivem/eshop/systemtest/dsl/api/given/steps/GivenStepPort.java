package com.optivem.eshop.systemtest.dsl.api.given.steps;

import com.optivem.eshop.systemtest.dsl.api.given.GivenPort;
import com.optivem.eshop.systemtest.dsl.api.when.WhenPort;

public interface GivenStepPort {
    GivenPort and();

    WhenPort when();
}
