package com.optivem.eshop.systemtest.core.gherkin.port;

public interface ScenarioDslPort {
    GivenPort given();
    WhenPort when();
    void markAsExecuted();
}
