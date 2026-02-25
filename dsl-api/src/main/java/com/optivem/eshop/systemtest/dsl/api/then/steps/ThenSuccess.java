package com.optivem.eshop.systemtest.dsl.api.then.steps;

import com.optivem.eshop.systemtest.dsl.api.then.steps.base.ThenStep;

public interface ThenSuccess extends ThenStep<ThenSuccess> {
    ThenOrder order();

    ThenOrder order(String orderNumber);

    ThenCoupon coupon();

    ThenCoupon coupon(String couponCode);
}

