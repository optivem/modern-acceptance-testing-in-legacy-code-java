package com.optivem.eshop.systemtest.core.gherkin.port;

public interface GivenPort {
    GivenProductPort product();

    GivenOrderPort order();

    GivenClockPort clock();

    GivenCountryPort country();

    GivenCouponPort coupon();

    WhenPort when();
}
