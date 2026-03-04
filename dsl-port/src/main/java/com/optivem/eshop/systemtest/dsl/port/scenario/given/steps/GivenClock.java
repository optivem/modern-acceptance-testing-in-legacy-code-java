package com.optivem.eshop.systemtest.dsl.port.scenario.given.steps;

import com.optivem.eshop.systemtest.dsl.port.scenario.given.steps.base.GivenStep;

public interface GivenClock extends GivenStep {
    GivenClock withTime(String time);
}
