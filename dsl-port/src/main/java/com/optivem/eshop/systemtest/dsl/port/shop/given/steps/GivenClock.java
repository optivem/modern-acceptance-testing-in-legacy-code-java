package com.optivem.eshop.systemtest.dsl.port.shop.given.steps;

import com.optivem.eshop.systemtest.dsl.port.shop.given.steps.base.GivenStep;

public interface GivenClock extends GivenStep {
    GivenClock withTime(String time);
}

