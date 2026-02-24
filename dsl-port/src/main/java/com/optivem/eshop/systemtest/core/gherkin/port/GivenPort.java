package com.optivem.eshop.systemtest.dsl.api;

public interface GivenPort {
    GivenProductPort product();

    GivenOrderPort order();

    GivenClockPort clock();

    GivenCountryPort country();

    GivenCouponPort coupon();

    WhenPort when();
}
