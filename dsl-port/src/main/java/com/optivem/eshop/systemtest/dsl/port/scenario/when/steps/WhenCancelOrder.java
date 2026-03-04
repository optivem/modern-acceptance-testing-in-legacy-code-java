package com.optivem.eshop.systemtest.dsl.port.scenario.when.steps;

import com.optivem.eshop.systemtest.dsl.port.scenario.when.steps.base.WhenStep;

public interface WhenCancelOrder extends WhenStep {
    WhenCancelOrder withOrderNumber(String orderNumber);
}
