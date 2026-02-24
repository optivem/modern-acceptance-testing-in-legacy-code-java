package com.optivem.eshop.systemtest.dsl.api.when;

import com.optivem.eshop.systemtest.dsl.api.when.steps.WhenCancelOrder;
import com.optivem.eshop.systemtest.dsl.api.when.steps.WhenPlaceOrder;
import com.optivem.eshop.systemtest.dsl.api.when.steps.WhenPublishCoupon;
import com.optivem.eshop.systemtest.dsl.api.when.steps.WhenStep;
import com.optivem.eshop.systemtest.dsl.api.when.steps.WhenViewOrder;

public interface When {
    WhenStep goToShop();

    WhenPlaceOrder placeOrder();

    WhenCancelOrder cancelOrder();

    WhenViewOrder viewOrder();

    WhenPublishCoupon publishCoupon();

    WhenStep browseCoupons();
}

