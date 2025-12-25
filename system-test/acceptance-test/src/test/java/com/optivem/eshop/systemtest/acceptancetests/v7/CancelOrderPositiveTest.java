package com.optivem.eshop.systemtest.acceptancetests.v7;

import com.optivem.eshop.systemtest.acceptancetests.v7.base.BaseAcceptanceTest;
import com.optivem.eshop.systemtest.core.shop.ChannelType;
import com.optivem.eshop.systemtest.core.shop.client.dtos.enums.OrderStatus;
import com.optivem.testing.channels.Channel;
import org.junit.jupiter.api.TestTemplate;

import static com.optivem.eshop.systemtest.acceptancetests.commons.constants.Defaults.ORDER_NUMBER;
import static com.optivem.eshop.systemtest.acceptancetests.commons.constants.Defaults.SKU;

public class CancelOrderPositiveTest extends BaseAcceptanceTest {

    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    void shouldCancelOrder() {
        scenario
                .given()
                .product()
                .withSku(SKU)
                .and().taxRate().withCountry("US").withTaxRate(0.0)
                .and().order()
                .withOrderNumber(ORDER_NUMBER)
                .withSku(SKU)
                .when()
                .cancelOrder()
                .withOrderNumber(ORDER_NUMBER)
                .then()
                .shouldSucceed()
                .and()
                .order(ORDER_NUMBER)
                .hasSku(SKU)
                .hasStatus(OrderStatus.CANCELLED);
    }
}

