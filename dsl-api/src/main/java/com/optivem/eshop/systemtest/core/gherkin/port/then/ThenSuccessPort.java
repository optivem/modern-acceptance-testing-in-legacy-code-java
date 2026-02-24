package com.optivem.eshop.systemtest.dsl.api;

public interface ThenSuccessPort {
    ThenSuccessPort and();

    ThenOrderPort order();

    ThenOrderPort order(String orderNumber);

    ThenCouponPort coupon();

    ThenCouponPort coupon(String couponCode);
}
