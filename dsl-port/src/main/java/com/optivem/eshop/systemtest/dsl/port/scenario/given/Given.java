package com.optivem.eshop.systemtest.dsl.port.scenario.given;

import com.optivem.eshop.systemtest.dsl.port.scenario.given.steps.GivenClock;
import com.optivem.eshop.systemtest.dsl.port.scenario.given.steps.GivenCountry;
import com.optivem.eshop.systemtest.dsl.port.scenario.given.steps.GivenCoupon;
import com.optivem.eshop.systemtest.dsl.port.scenario.given.steps.GivenOrder;
import com.optivem.eshop.systemtest.dsl.port.scenario.given.steps.GivenProduct;
import com.optivem.eshop.systemtest.dsl.port.scenario.then.Then;
import com.optivem.eshop.systemtest.dsl.port.scenario.when.When;

public interface Given {
    GivenClock clock();

    GivenProduct product();

    GivenCountry country();

    GivenOrder order();

    GivenCoupon coupon();

    When when();

    Then then();
}
