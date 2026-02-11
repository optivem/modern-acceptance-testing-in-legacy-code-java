package com.optivem.eshop.systemtest.acceptancetests.v7.orders;

import com.optivem.eshop.systemtest.acceptancetests.v7.base.BaseAcceptanceTest;
import com.optivem.eshop.systemtest.core.shop.ChannelType;
import com.optivem.eshop.systemtest.core.shop.commons.dtos.orders.OrderStatus;
import com.optivem.testing.Channel;
import org.junit.jupiter.api.TestTemplate;

public class Temp extends BaseAcceptanceTest {

    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    void cannotPlaceOrderWithCouponThatHasExceededUsageLimit2() {
        scenario
                .given().coupon().withCouponCode("LIMITED2024").withUsageLimit(2)
                .and().order().withOrderNumber("ORD-1").withCouponCode("LIMITED2024")
                .and().order().withOrderNumber("ORD-2").withCouponCode("LIMITED2024")
                .when().placeOrder().withOrderNumber("ORD-3").withCouponCode("LIMITED2024")
                .then().shouldFail().errorMessage("The request contains one or more validation errors")
                .fieldErrorMessage("couponCode", "Coupon code LIMITED2024 has exceeded its usage limit")
                .and().coupon("LIMITED2024").hasUsedCount(2)
                .and().order("ORD-1").hasOrderNumberPrefix("ORD-").hasStatus(OrderStatus.PLACED);
    }
}
