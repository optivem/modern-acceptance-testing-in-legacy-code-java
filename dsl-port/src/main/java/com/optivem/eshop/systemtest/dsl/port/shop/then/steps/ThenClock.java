package com.optivem.eshop.systemtest.dsl.port.shop.then.steps;

public interface ThenClock {
    ThenClock hasTime(String time);

    ThenClock hasTimeNotNull();
}
