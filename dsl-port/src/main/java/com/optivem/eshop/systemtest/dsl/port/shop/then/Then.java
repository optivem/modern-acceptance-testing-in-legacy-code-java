package com.optivem.eshop.systemtest.dsl.port.shop.then;

import com.optivem.eshop.systemtest.dsl.port.shop.then.steps.ThenClock;
import com.optivem.eshop.systemtest.dsl.port.shop.then.steps.ThenFailure;
import com.optivem.eshop.systemtest.dsl.port.shop.then.steps.ThenSuccess;

public interface Then {
    ThenClock clock();

    ThenSuccess shouldSucceed();

    ThenFailure shouldFail();
}

