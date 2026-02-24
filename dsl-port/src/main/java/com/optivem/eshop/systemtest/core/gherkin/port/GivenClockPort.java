package com.optivem.eshop.systemtest.core.gherkin.port;

public interface GivenClockPort {
    GivenClockPort withTime(String time);

    GivenPort and();

    WhenPort when();
}
