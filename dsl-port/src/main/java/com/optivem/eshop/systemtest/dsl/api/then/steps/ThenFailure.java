package com.optivem.eshop.systemtest.dsl.api.then.steps;

import com.optivem.eshop.systemtest.dsl.api.then.steps.base.ThenStep;

public interface ThenFailure extends ThenStep<ThenFailure> {
    ThenFailure errorMessage(String expectedMessage);

    ThenFailure fieldErrorMessage(String expectedField, String expectedMessage);

    ThenOrder order();

    ThenOrder order(String orderNumber);

    ThenCoupon coupon();

    ThenCoupon coupon(String couponCode);
}

