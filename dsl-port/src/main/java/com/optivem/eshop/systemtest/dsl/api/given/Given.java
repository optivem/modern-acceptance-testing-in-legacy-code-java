package com.optivem.eshop.systemtest.dsl.api.given;

import com.optivem.eshop.systemtest.dsl.api.given.steps.GivenClock;
import com.optivem.eshop.systemtest.dsl.api.given.steps.GivenCountry;
import com.optivem.eshop.systemtest.dsl.api.given.steps.GivenCoupon;
import com.optivem.eshop.systemtest.dsl.api.given.steps.GivenOrder;
import com.optivem.eshop.systemtest.dsl.api.given.steps.GivenProduct;
import com.optivem.eshop.systemtest.dsl.api.when.When;

public interface Given {
    GivenProduct product();

    GivenOrder order();

    GivenClock clock();

    GivenCountry country();

    GivenCoupon coupon();

    When when();
}

