package com.optivem.eshop.systemtest.dsl.api.given;

import com.optivem.eshop.systemtest.dsl.api.when.WhenPort;

public interface GivenStepPort {
    GivenPort and();

    WhenPort when();
}
