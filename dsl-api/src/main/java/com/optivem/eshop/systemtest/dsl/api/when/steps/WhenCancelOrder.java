package com.optivem.eshop.systemtest.dsl.api.when.steps;

import com.optivem.eshop.systemtest.dsl.api.then.Then;
import com.optivem.eshop.systemtest.dsl.api.when.steps.base.WhenStep;

public interface WhenCancelOrder extends WhenStep {
    WhenCancelOrder withOrderNumber(String orderNumber);
}

