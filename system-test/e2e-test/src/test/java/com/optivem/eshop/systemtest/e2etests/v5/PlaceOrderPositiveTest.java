package com.optivem.eshop.systemtest.e2etests.v5;

import com.optivem.eshop.systemtest.core.shop.ChannelType;
import com.optivem.eshop.systemtest.core.shop.commons.dtos.enums.OrderStatus;
import com.optivem.eshop.systemtest.e2etests.v5.base.BaseE2eTest;
import com.optivem.testing.channels.Channel;
import com.optivem.testing.channels.DataSource;
import org.junit.jupiter.api.TestTemplate;

import static com.optivem.eshop.systemtest.e2etests.commons.constants.Defaults.ORDER_NUMBER;
import static com.optivem.eshop.systemtest.e2etests.commons.constants.Defaults.SKU;

public class PlaceOrderPositiveTest extends BaseE2eTest {

    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    void shouldPlaceOrderWithCorrectSubtotalPrice() {
        app.erp().returnsProduct()
                .sku("ABC")
                .unitPrice(20.00)
                .execute()
                .shouldSucceed();

        app.shop().placeOrder().orderNumber("ORDER-1001")
                .sku("ABC")
                .quantity(5)
                .execute()
                .shouldSucceed();

        app.shop().viewOrder().orderNumber("ORDER-1001").execute()
                .shouldSucceed()
                .subtotalPrice(100.00);
    }

    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    @DataSource({"20.00", "5", "100.00"})
    @DataSource({"10.00", "3", "30.00"})
    @DataSource({"15.50", "4", "62.00"})
    @DataSource({"99.99", "1", "99.99"})
    void shouldPlaceOrderWithCorrectSubtotalPriceParameterized(String unitPrice, String quantity, String subtotalPrice) {
        app.erp().returnsProduct()
                .sku("ABC")
                .unitPrice(unitPrice)
                .execute()
                .shouldSucceed();

        app.shop().placeOrder()
                .orderNumber("ORDER-1001")
                .sku("ABC")
                .quantity(quantity)
                .execute()
                .shouldSucceed();

        app.shop().viewOrder()
                .orderNumber("ORDER-1001")
                .execute()
                .shouldSucceed()
                .subtotalPrice(subtotalPrice);
    }

    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    void shouldPlaceOrder() {
        app.erp().returnsProduct()
                .sku(SKU)
                .unitPrice(20.00)
                .execute()
                .shouldSucceed();

        app.shop().placeOrder()
                .orderNumber(ORDER_NUMBER)
                .sku(SKU)
                .quantity(5)
                .country("US")
                .execute()
                .shouldSucceed()
                .orderNumber(ORDER_NUMBER)
                .orderNumberStartsWith("ORD-");

        app.shop().viewOrder()
                .orderNumber(ORDER_NUMBER)
                .execute()
                .shouldSucceed()
                .orderNumber(ORDER_NUMBER)
                .sku(SKU)
                .quantity(5)
                .country("US")
                .unitPrice(20.00)
                .subtotalPrice(100.00)
                .status(OrderStatus.PLACED)
                .discountRateGreaterThanOrEqualToZero()
                .discountAmountGreaterThanOrEqualToZero()
                .subtotalPriceGreaterThanZero()
                .taxRateGreaterThanOrEqualToZero()
                .taxAmountGreaterThanOrEqualToZero()
                .totalPriceGreaterThanZero();
    }
}
