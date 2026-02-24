package com.optivem.eshop.systemtest.dsl.api.when.steps;

import com.optivem.eshop.systemtest.dsl.api.then.Then;

public interface WhenCancelOrder {
    WhenCancelOrder withOrderNumber(String orderNumber);

    Then then();
}

