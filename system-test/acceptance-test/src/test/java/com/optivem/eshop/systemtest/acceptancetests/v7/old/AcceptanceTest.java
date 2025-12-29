package com.optivem.eshop.systemtest.acceptancetests.v7.old;

import com.optivem.eshop.systemtest.acceptancetests.v7.base.BaseAcceptanceTest;
import com.optivem.eshop.systemtest.core.shop.ChannelType;
import com.optivem.testing.channels.Channel;
import org.junit.jupiter.api.TestTemplate;

import java.time.Instant;

public class AcceptanceTest extends BaseAcceptanceTest {

    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    void shouldPlaceOrderWithCorrectSubtotalPrice() {
        scenario
                .given()
                .clock()
                .withTime("2025-12-24T17:01:00Z")
                .and()
                .product()
                .withSku("SKU-123")
                .withUnitPrice(20.00)
                .and()
                .taxRate()
                .withCountry("GORA")
                .withTaxRate(0.05)
                .when()
                .placeOrder()
                .withOrderNumber("ORDER-1001")
                .withSku("SKU-123")
                .withQuantity(5)
                .withCountry("GORA")
                .then()
                .shouldSucceed()
                .and()
                .order("ORDER-1001")
                .shouldHaveSubtotalPrice(100.00);
                // TODO: VJ: Need independence for controlling time
                // .hasDiscountRate(0.15);
    }
}

