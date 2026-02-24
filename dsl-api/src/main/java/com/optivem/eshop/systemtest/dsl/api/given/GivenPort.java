package com.optivem.eshop.systemtest.dsl.api.given;

import com.optivem.eshop.systemtest.dsl.api.when.WhenPort;

public interface GivenPort {
    GivenProductPort product();

    GivenOrderPort order();

    GivenClockPort clock();

    GivenCountryPort country();

    GivenCouponPort coupon();

    WhenPort when();
}
