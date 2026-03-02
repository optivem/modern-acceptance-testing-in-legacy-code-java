package com.optivem.eshop.systemtest.dsl.port.then;

import com.optivem.eshop.systemtest.dsl.port.then.steps.ThenFailure;
import com.optivem.eshop.systemtest.dsl.port.then.steps.ThenSuccess;

public interface Then {
    ThenSuccess shouldSucceed();

    ThenFailure shouldFail();
}

