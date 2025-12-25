package com.optivem.eshop.systemtest.e2etests.v5;

import com.optivem.eshop.systemtest.core.shop.ChannelType;
import com.optivem.eshop.systemtest.core.shop.client.dtos.enums.OrderStatus;
import com.optivem.eshop.systemtest.e2etests.v5.base.BaseE2eTest;
import com.optivem.testing.channels.Channel;
import org.junit.jupiter.api.TestTemplate;

public class CancelOrderPositiveTest extends BaseE2eTest {
    private static final String ORDER_NUMBER = "order-number";
    private static final String SKU = "sku";

    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    void shouldCancelOrder() {
        app.erp().returnsProduct()
                .sku(SKU)
                .execute()
                .shouldSucceed();

        app.shop().placeOrder()
                .orderNumber(ORDER_NUMBER)
                .sku(SKU)
                .execute()
                .shouldSucceed();

        app.shop().cancelOrder()
                .orderNumber(ORDER_NUMBER)
                .execute()
                .shouldSucceed();

        app.shop().viewOrder()
                .orderNumber(ORDER_NUMBER)
                .execute()
                .shouldSucceed()
                .orderNumber(ORDER_NUMBER)
                .sku(SKU)
                .status(OrderStatus.CANCELLED);
    }
}
