package com.optivem.eshop.systemtest.e2etests.v5;

import com.optivem.eshop.systemtest.core.shop.ChannelType;
import com.optivem.eshop.systemtest.core.shop.client.dtos.enums.OrderStatus;
import com.optivem.eshop.systemtest.e2etests.v5.base.BaseE2eTest;
import com.optivem.testing.channels.Channel;
import org.junit.jupiter.api.TestTemplate;

import static com.optivem.eshop.systemtest.e2etests.commons.constants.Defaults.ORDER_NUMBER;
import static com.optivem.eshop.systemtest.e2etests.commons.constants.Defaults.SKU;

public class ViewOrderPositiveTest extends BaseE2eTest {

    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    void shouldViewPlacedOrder() {
        app.erp().returnsProduct()
                .sku(SKU)
                .unitPrice(25.00)
                .execute()
                .shouldSucceed();

        app.shop().placeOrder()
                .orderNumber(ORDER_NUMBER)
                .sku(SKU)
                .quantity(4)
                .country("US")
                .execute()
                .shouldSucceed();

        app.shop().viewOrder()
                .orderNumber(ORDER_NUMBER)
                .execute()
                .shouldSucceed()
                .orderNumber(ORDER_NUMBER)
                .sku(SKU)
                .quantity(4)
                .country("US")
                .unitPrice(25.00)
                .subtotalPrice(100.00)
                .status(OrderStatus.PLACED)
                .discountRateGreaterThanOrEqualToZero()
                .discountAmountGreaterThanOrEqualToZero()
                .preTaxTotalGreaterThanZero()
                .taxRateGreaterThanOrEqualToZero()
                .taxAmountGreaterThanOrEqualToZero()
                .totalPriceGreaterThanZero();
    }
}
