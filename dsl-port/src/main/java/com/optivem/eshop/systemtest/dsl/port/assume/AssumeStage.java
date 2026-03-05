package com.optivem.eshop.systemtest.dsl.port.assume;

import com.optivem.eshop.systemtest.dsl.port.assume.steps.AssumeRunning;

public interface AssumeStage {
    AssumeRunning shop();

    AssumeRunning erp();

    AssumeRunning tax();

    AssumeRunning clock();
}


