package com.optivem.eshop.systemtest.dsl.api.when.steps;

import com.optivem.eshop.systemtest.dsl.api.then.ThenPort;

public interface WhenCancelOrderPort {
    WhenCancelOrderPort withOrderNumber(String orderNumber);

    ThenPort then();
}
