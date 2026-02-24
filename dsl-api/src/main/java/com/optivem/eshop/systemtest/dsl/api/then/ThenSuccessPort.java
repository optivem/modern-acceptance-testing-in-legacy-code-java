package com.optivem.eshop.systemtest.dsl.api.then;

public interface ThenSuccessPort {
    ThenSuccessPort and();

    ThenOrderPort order();

    ThenOrderPort order(String orderNumber);

    ThenCouponPort coupon();

    ThenCouponPort coupon(String couponCode);
}
