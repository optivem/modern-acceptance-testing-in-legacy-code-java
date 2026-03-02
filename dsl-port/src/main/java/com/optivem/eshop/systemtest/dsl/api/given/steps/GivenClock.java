package com.optivem.eshop.systemtest.dsl.api.given.steps;

import com.optivem.eshop.systemtest.dsl.api.given.steps.base.GivenStep;

public interface GivenClock extends GivenStep {
    GivenClock withTime(String time);

}

