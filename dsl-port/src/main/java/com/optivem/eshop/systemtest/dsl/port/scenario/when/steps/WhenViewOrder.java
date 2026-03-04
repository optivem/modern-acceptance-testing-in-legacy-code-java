package com.optivem.eshop.systemtest.dsl.port.scenario.when.steps;

import com.optivem.eshop.systemtest.dsl.port.scenario.when.steps.base.WhenStep;

public interface WhenViewOrder extends WhenStep {
    WhenViewOrder withOrderNumber(String orderNumber);
}
