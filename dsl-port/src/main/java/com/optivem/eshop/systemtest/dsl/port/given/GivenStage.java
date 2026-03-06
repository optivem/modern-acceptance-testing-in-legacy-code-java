package com.optivem.eshop.systemtest.dsl.port.given;

import com.optivem.eshop.systemtest.dsl.port.given.steps.GivenClock;
import com.optivem.eshop.systemtest.dsl.port.given.steps.GivenCountry;
import com.optivem.eshop.systemtest.dsl.port.given.steps.GivenCoupon;
import com.optivem.eshop.systemtest.dsl.port.given.steps.GivenOrder;
import com.optivem.eshop.systemtest.dsl.port.given.steps.GivenProduct;
import com.optivem.eshop.systemtest.dsl.port.given.steps.GivenReview;
import com.optivem.eshop.systemtest.dsl.port.then.ThenStage;
import com.optivem.eshop.systemtest.dsl.port.when.WhenStage;

public interface GivenStage {
    GivenClock clock();

    GivenProduct product();

    GivenCountry country();

    GivenOrder order();

    GivenCoupon coupon();

    GivenReview review();

    WhenStage when();

    ThenStage then();
}


