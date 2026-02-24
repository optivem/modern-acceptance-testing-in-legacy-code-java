package com.optivem.eshop.systemtest.dsl.api;

public interface ThenFailurePort {
    ThenFailurePort errorMessage(String expectedMessage);

    ThenFailurePort fieldErrorMessage(String expectedField, String expectedMessage);

    ThenFailurePort and();

    ThenOrderPort order();

    ThenOrderPort order(String orderNumber);

    ThenCouponPort coupon();

    ThenCouponPort coupon(String couponCode);
}
