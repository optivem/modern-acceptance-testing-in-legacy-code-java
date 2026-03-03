package com.optivem.eshop.systemtest.dsl.port.clock.given.steps;

import com.optivem.eshop.systemtest.dsl.port.clock.given.steps.base.GivenStep;

public interface GivenReturnsTime extends GivenStep {
    GivenReturnsTime time(String time);
}
