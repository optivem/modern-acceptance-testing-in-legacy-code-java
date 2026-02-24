package com.optivem.eshop.systemtest.dsl.api.when;

import com.optivem.eshop.systemtest.dsl.api.when.steps.WhenCancelOrderPort;
import com.optivem.eshop.systemtest.dsl.api.when.steps.WhenPlaceOrderPort;
import com.optivem.eshop.systemtest.dsl.api.when.steps.WhenPublishCouponPort;
import com.optivem.eshop.systemtest.dsl.api.when.steps.WhenStepPort;
import com.optivem.eshop.systemtest.dsl.api.when.steps.WhenViewOrderPort;

public interface WhenPort {
    WhenStepPort goToShop();

    WhenPlaceOrderPort placeOrder();

    WhenCancelOrderPort cancelOrder();

    WhenViewOrderPort viewOrder();

    WhenPublishCouponPort publishCoupon();

    WhenStepPort browseCoupons();
}
