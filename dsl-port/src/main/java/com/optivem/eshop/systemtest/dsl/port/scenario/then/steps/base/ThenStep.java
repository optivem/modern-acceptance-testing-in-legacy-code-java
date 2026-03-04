package com.optivem.eshop.systemtest.dsl.port.scenario.then.steps.base;

import com.optivem.eshop.systemtest.dsl.port.scenario.then.steps.ThenCoupon;
import com.optivem.eshop.systemtest.dsl.port.scenario.then.steps.ThenOrder;

public interface ThenStep<TThen> {
    TThen and();

    ThenOrder order();

    ThenOrder order(String orderNumber);

    ThenCoupon coupon();

    ThenCoupon coupon(String couponCode);
}
