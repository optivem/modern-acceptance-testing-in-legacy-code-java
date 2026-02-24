package com.optivem.eshop.systemtest.dsl.api.then.steps;

public interface ThenSuccess {
    ThenSuccess and();

    ThenOrder order();

    ThenOrder order(String orderNumber);

    ThenCoupon coupon();

    ThenCoupon coupon(String couponCode);
}

