package com.optivem.eshop.systemtest.dsl.port;

import com.optivem.eshop.systemtest.dsl.port.background.BackgroundDsl;
import com.optivem.eshop.systemtest.dsl.port.scenario.ScenarioDsl;

public interface FeatureDsl {
    BackgroundDsl background();

    ScenarioDsl scenario();
}
