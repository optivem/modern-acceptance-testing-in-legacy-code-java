package com.optivem.eshop.systemtest.core.gherkin.port;

public interface ThenFailurePort {
    ThenFailurePort errorMessage(String expectedMessage);

    ThenFailurePort fieldErrorMessage(String expectedField, String expectedMessage);

    ThenFailurePort and();

    ThenOrderPort order();

    ThenOrderPort order(String orderNumber);

    ThenCouponPort coupon();

    ThenCouponPort coupon(String couponCode);
}
