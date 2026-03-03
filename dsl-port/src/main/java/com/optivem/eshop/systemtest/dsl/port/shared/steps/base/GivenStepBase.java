package com.optivem.eshop.systemtest.dsl.port.shared.steps.base;

public interface GivenStepBase<TGiven, TWhen> {
    TGiven and();

    TWhen when();
}
