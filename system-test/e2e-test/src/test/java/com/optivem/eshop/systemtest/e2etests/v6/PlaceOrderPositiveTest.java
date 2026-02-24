package com.optivem.eshop.systemtest.e2etests.v6;

import com.optivem.eshop.systemtest.core.shop.ChannelType;
import com.optivem.eshop.systemtest.driver.api.shop.commons.dtos.orders.OrderStatus;
import com.optivem.eshop.systemtest.e2etests.v6.base.BaseE2eTest;
import com.optivem.testing.Channel;
import com.optivem.testing.DataSource;
import org.junit.jupiter.api.TestTemplate;

class PlaceOrderPositiveTest extends BaseE2eTest {

    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    void shouldPlaceOrderWithCorrectSubtotalPrice() {
        scenario
                .given().product().withUnitPrice(20.00)
                .when().placeOrder().withQuantity(5)
                .then().shouldSucceed()
                .and().order().hasSubtotalPrice(100.00);
    }

    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    @DataSource({"20.00", "5", "100.00"})
    @DataSource({"10.00", "3", "30.00"})
    @DataSource({"15.50", "4", "62.00"})
    @DataSource({"99.99", "1", "99.99"})
    void shouldPlaceOrderWithCorrectSubtotalPriceParameterized(String unitPrice, String quantity, String subtotalPrice) {
        scenario
                .given().product().withUnitPrice(unitPrice)
                .when().placeOrder().withQuantity(quantity)
                .then().shouldSucceed()
                .and().order().hasSubtotalPrice(subtotalPrice);
    }

    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    void shouldPlaceOrder() {
        scenario
                .given().product().withUnitPrice(20.00)
                .when().placeOrder().withQuantity(5)
                .then().shouldSucceed()
                .and().order()
                .hasOrderNumberPrefix("ORD-")
                .hasQuantity(5)
                .hasUnitPrice(20.00)
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


