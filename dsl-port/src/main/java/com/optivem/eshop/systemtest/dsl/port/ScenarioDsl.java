package com.optivem.eshop.systemtest.dsl.port;

import com.optivem.eshop.systemtest.dsl.port.assume.AssumeStage;
import com.optivem.eshop.systemtest.dsl.port.given.GivenStage;
import com.optivem.eshop.systemtest.dsl.port.when.WhenStage;

public interface ScenarioDsl {
    AssumeStage assume();

    GivenStage given();

    WhenStage when();
}