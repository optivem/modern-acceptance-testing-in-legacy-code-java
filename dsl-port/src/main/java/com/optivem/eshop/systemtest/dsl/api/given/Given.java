package com.optivem.eshop.systemtest.dsl.port.given;

import com.optivem.eshop.systemtest.dsl.port.given.steps.GivenClock;
import com.optivem.eshop.systemtest.dsl.port.given.steps.GivenCountry;
import com.optivem.eshop.systemtest.dsl.port.given.steps.GivenCoupon;
import com.optivem.eshop.systemtest.dsl.port.given.steps.GivenOrder;
import com.optivem.eshop.systemtest.dsl.port.given.steps.GivenProduct;
import com.optivem.eshop.systemtest.dsl.port.when.When;

public interface Given {
    GivenProduct product();

    GivenOrder order();

    GivenClock clock();

    GivenCountry country();

    GivenCoupon coupon();

    When when();
}

