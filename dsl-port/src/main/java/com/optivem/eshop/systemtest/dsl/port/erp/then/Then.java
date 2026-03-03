package com.optivem.eshop.systemtest.dsl.port.erp.then;

import com.optivem.eshop.systemtest.dsl.port.erp.then.steps.ThenFailure;
import com.optivem.eshop.systemtest.dsl.port.erp.then.steps.ThenSuccess;

public interface Then {
    ThenSuccess shouldSucceed();

    ThenFailure shouldFail();
}
