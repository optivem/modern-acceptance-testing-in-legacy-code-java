package com.optivem.eshop.systemtest.dsl.api;

import com.optivem.eshop.systemtest.dsl.api.given.GivenPort;
import com.optivem.eshop.systemtest.dsl.api.when.WhenPort;

public interface ScenarioDslPort {
    GivenPort given();
    WhenPort when();
    void markAsExecuted();
}
