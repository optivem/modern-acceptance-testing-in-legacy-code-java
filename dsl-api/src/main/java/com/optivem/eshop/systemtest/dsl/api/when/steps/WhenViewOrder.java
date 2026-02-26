package com.optivem.eshop.systemtest.dsl.api.when.steps;

import com.optivem.eshop.systemtest.dsl.api.when.steps.base.WhenStep;

public interface WhenViewOrder extends WhenStep {
    WhenViewOrder withOrderNumber(String orderNumber);
}

