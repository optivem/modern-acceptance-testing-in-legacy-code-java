package com.optivem.eshop.systemtest.dsl.port.scenario.when;

import com.optivem.eshop.systemtest.dsl.port.scenario.when.steps.WhenCancelOrder;
import com.optivem.eshop.systemtest.dsl.port.scenario.when.steps.WhenPlaceOrder;
import com.optivem.eshop.systemtest.dsl.port.scenario.when.steps.WhenPublishCoupon;
import com.optivem.eshop.systemtest.dsl.port.scenario.when.steps.WhenViewOrder;
import com.optivem.eshop.systemtest.dsl.port.scenario.when.steps.base.WhenStep;

public interface When {
    WhenStep goToShop();

    WhenPlaceOrder placeOrder();

    WhenCancelOrder cancelOrder();

    WhenViewOrder viewOrder();

    WhenPublishCoupon publishCoupon();

    WhenStep browseCoupons();

    WhenStep goToErp();

    WhenStep goToTax();

    WhenStep goToClock();
}
