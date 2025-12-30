package com.optivem.eshop.systemtest.e2etests.v6;

import com.optivem.eshop.systemtest.core.shop.ChannelType;
import com.optivem.eshop.systemtest.core.shop.commons.dtos.enums.OrderStatus;
import com.optivem.eshop.systemtest.e2etests.v6.base.BaseE2eTest;
import com.optivem.testing.channels.Channel;
import org.junit.jupiter.api.TestTemplate;

import static com.optivem.eshop.systemtest.e2etests.commons.constants.Defaults.ORDER_NUMBER;
import static com.optivem.eshop.systemtest.e2etests.commons.constants.Defaults.SKU;

public class ViewOrderPositiveTest extends BaseE2eTest {

    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    void shouldViewPlacedOrder() {
        scenario
                .given()
                .product()
                .withSku(SKU)
                .withUnitPrice(25.00)
                .and().order()
                .withOrderNumber(ORDER_NUMBER)
                .withSku(SKU)
                .withQuantity(4)
                .when()
                .viewOrder()
                .withOrderNumber(ORDER_NUMBER)
                .then()
                .shouldSucceed()
                .and()
                .order(ORDER_NUMBER)
                .hasSku(SKU)
                .hasQuantity(4)
                .hasCountry("US")
                .hasUnitPrice(25.00)
                .shouldHaveSubtotalPrice(100.00)
                .hasStatus(OrderStatus.PLACED)
                .hasDiscountRateGreaterThanOrEqualToZero()
                .hasDiscountAmountGreaterThanOrEqualToZero()
                .hasSubtotalPriceGreaterThanZero()
                .hasTaxRateGreaterThanOrEqualToZero()
                .hasTaxAmountGreaterThanOrEqualToZero()
                .hasTotalPriceGreaterThanZero();
    }
}

