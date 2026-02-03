package com.optivem.eshop.systemtest.e2etests.v6;

import com.optivem.eshop.systemtest.core.shop.ChannelType;
import com.optivem.eshop.systemtest.core.shop.commons.dtos.orders.OrderStatus;
import com.optivem.eshop.systemtest.e2etests.v6.base.BaseE2eTest;
import com.optivem.test.Channel;
import org.junit.jupiter.api.TestTemplate;

import static com.optivem.eshop.systemtest.e2etests.commons.constants.Defaults.*;

public class ViewOrderPositiveTest extends BaseE2eTest {

    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    void shouldViewPlacedOrder() {
        scenario
                .given().product().withSku(SKU).withUnitPrice(25.00)
                .and().order().withOrderNumber(ORDER_NUMBER).withSku(SKU).withCountry(COUNTRY).withQuantity(4)
                .when().viewOrder().withOrderNumber(ORDER_NUMBER)
                .then().shouldSucceed()
                .and().order(ORDER_NUMBER)
                .hasSku(SKU)
                .hasQuantity(4)
                .hasCountry(COUNTRY)
                .hasUnitPrice(25.00)
                .hasSubtotalPrice(100.00)
                .hasStatus(OrderStatus.PLACED)
                .hasDiscountRateGreaterThanOrEqualToZero()
                .hasDiscountAmountGreaterThanOrEqualToZero()
                .hasSubtotalPriceGreaterThanZero()
                .hasTaxRateGreaterThanOrEqualToZero()
                .hasTaxAmountGreaterThanOrEqualToZero()
                .hasTotalPriceGreaterThanZero();
    }
}


