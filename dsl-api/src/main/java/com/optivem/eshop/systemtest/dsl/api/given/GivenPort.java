package com.optivem.eshop.systemtest.dsl.api.given;

import com.optivem.eshop.systemtest.dsl.api.given.steps.GivenClockPort;
import com.optivem.eshop.systemtest.dsl.api.given.steps.GivenCountryPort;
import com.optivem.eshop.systemtest.dsl.api.given.steps.GivenCouponPort;
import com.optivem.eshop.systemtest.dsl.api.given.steps.GivenOrderPort;
import com.optivem.eshop.systemtest.dsl.api.given.steps.GivenProductPort;
import com.optivem.eshop.systemtest.dsl.api.when.WhenPort;

public interface GivenPort {
    GivenProductPort product();

    GivenOrderPort order();

    GivenClockPort clock();

    GivenCountryPort country();

    GivenCouponPort coupon();

    WhenPort when();
}
