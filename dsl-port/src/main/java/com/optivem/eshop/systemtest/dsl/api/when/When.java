package com.optivem.eshop.systemtest.dsl.port.when;

import com.optivem.eshop.systemtest.dsl.port.when.steps.WhenCancelOrder;
import com.optivem.eshop.systemtest.dsl.port.when.steps.WhenPlaceOrder;
import com.optivem.eshop.systemtest.dsl.port.when.steps.WhenPublishCoupon;
import com.optivem.eshop.systemtest.dsl.port.when.steps.base.WhenStep;
import com.optivem.eshop.systemtest.dsl.port.when.steps.WhenViewOrder;

public interface When {
    WhenStep goToShop();

    WhenPlaceOrder placeOrder();

    WhenCancelOrder cancelOrder();

    WhenViewOrder viewOrder();

    WhenPublishCoupon publishCoupon();

    WhenStep browseCoupons();
}

