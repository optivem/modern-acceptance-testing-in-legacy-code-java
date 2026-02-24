package com.optivem.eshop.systemtest.dsl.api.when;

import com.optivem.eshop.systemtest.dsl.api.then.ThenPort;

public interface WhenViewOrderPort {
    WhenViewOrderPort withOrderNumber(String orderNumber);

    ThenPort then();
}
