package com.optivem.eshop.systemtest.dsl.port.shop.when;

import com.optivem.eshop.systemtest.dsl.port.shop.when.steps.WhenCancelOrder;
import com.optivem.eshop.systemtest.dsl.port.shop.when.steps.WhenPlaceOrder;
import com.optivem.eshop.systemtest.dsl.port.shop.when.steps.WhenPublishCoupon;
import com.optivem.eshop.systemtest.dsl.port.shop.when.steps.WhenViewOrder;
import com.optivem.eshop.systemtest.dsl.port.shop.when.steps.base.WhenStep;

public interface When {
    WhenStep goToShop();

    WhenPlaceOrder placeOrder();

    WhenCancelOrder cancelOrder();

    WhenViewOrder viewOrder();

    WhenPublishCoupon publishCoupon();

    WhenStep browseCoupons();

    WhenStep goToErp();
}

