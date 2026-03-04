package com.optivem.eshop.systemtest.dsl.port.then;

import com.optivem.eshop.systemtest.dsl.port.then.steps.ThenFailure;
import com.optivem.eshop.systemtest.dsl.port.then.steps.ThenSuccess;

public interface ThenResultStage extends ThenStage {
    ThenSuccess shouldSucceed();

    ThenFailure shouldFail();
}
