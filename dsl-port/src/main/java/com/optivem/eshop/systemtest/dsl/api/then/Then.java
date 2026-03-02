package com.optivem.eshop.systemtest.dsl.api.then;

import com.optivem.eshop.systemtest.dsl.api.then.steps.ThenFailure;
import com.optivem.eshop.systemtest.dsl.api.then.steps.ThenSuccess;

public interface Then {
    ThenSuccess shouldSucceed();

    ThenFailure shouldFail();
}

