package com.optivem.eshop.systemtest.dsl.port.scenario.then;

import com.optivem.eshop.systemtest.dsl.port.scenario.then.steps.ThenFailure;
import com.optivem.eshop.systemtest.dsl.port.scenario.then.steps.ThenSuccess;

public interface ThenResult extends Then {
    ThenSuccess shouldSucceed();

    ThenFailure shouldFail();
}
