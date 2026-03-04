package com.optivem.eshop.systemtest.dsl.port.assume;

import com.optivem.eshop.systemtest.dsl.port.assume.steps.Should;

public interface AssumeStage {
    Should shop();

    Should erp();

    Should tax();

    Should clock();
}


