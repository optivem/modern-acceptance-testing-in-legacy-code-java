package com.optivem.eshop.systemtest.dsl.port.when.steps;

import com.optivem.eshop.systemtest.dsl.port.when.steps.base.WhenStep;

public interface WhenViewOrder extends WhenStep {
    WhenViewOrder withOrderNumber(String orderNumber);
}

