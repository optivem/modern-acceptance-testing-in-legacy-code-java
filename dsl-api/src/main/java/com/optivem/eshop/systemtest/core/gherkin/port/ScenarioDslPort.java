package com.optivem.eshop.systemtest.dsl.api;

public interface ScenarioDslPort {
    GivenPort given();
    WhenPort when();
    void markAsExecuted();
}
