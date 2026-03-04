package com.optivem.eshop.systemtest.dsl.port.shop.given;

import com.optivem.eshop.systemtest.dsl.port.shop.given.steps.GivenClock;
import com.optivem.eshop.systemtest.dsl.port.shop.given.steps.GivenCountry;
import com.optivem.eshop.systemtest.dsl.port.shop.given.steps.GivenCoupon;
import com.optivem.eshop.systemtest.dsl.port.shop.given.steps.GivenOrder;
import com.optivem.eshop.systemtest.dsl.port.shop.given.steps.GivenProduct;
import com.optivem.eshop.systemtest.dsl.port.shop.then.Then;
import com.optivem.eshop.systemtest.dsl.port.shop.when.When;

public interface Given {
    GivenProduct product();

    GivenOrder order();

    GivenClock clock();

    GivenCountry country();

    GivenCoupon coupon();

    When when();

    Then then();
}

