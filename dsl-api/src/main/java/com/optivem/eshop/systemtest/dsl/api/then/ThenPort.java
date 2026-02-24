package com.optivem.eshop.systemtest.dsl.api.then;

import com.optivem.eshop.systemtest.dsl.api.then.steps.ThenFailurePort;
import com.optivem.eshop.systemtest.dsl.api.then.steps.ThenSuccessPort;

public interface ThenPort {
    ThenSuccessPort shouldSucceed();

    ThenFailurePort shouldFail();
}
