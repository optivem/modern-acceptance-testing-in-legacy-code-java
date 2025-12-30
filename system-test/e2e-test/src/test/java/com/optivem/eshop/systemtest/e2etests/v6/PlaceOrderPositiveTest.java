package com.optivem.eshop.systemtest.e2etests.v6;

import com.optivem.eshop.systemtest.core.shop.ChannelType;
import com.optivem.eshop.systemtest.core.shop.commons.dtos.enums.OrderStatus;
import com.optivem.eshop.systemtest.e2etests.v6.base.BaseE2eTest;
import com.optivem.testing.channels.Channel;
import com.optivem.testing.channels.DataSource;
import org.junit.jupiter.api.TestTemplate;

import static com.optivem.eshop.systemtest.e2etests.commons.constants.Defaults.ORDER_NUMBER;
import static com.optivem.eshop.systemtest.e2etests.commons.constants.Defaults.SKU;

public class PlaceOrderPositiveTest extends BaseE2eTest {
    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    void shouldCalculateSubtotalPrice() {
        scenario
                .given().product().withSku("ABC").withUnitPrice(20.00)
                .when().placeOrder().withOrderNumber("ORDER-1001").withSku("ABC").withQuantity(5)
                .then().shouldSucceed()
                .and().order("ORDER-1001").shouldHaveSubtotalPrice(100.00);
    }

    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    void shouldPlaceOrderWithCorrectSubtotalPrice() {
        scenario
                .given()
                .product()
                .withSku("ABC")
                .withUnitPrice(20.00)
                .when()
                .placeOrder()
                .withOrderNumber("ORDER-1001")
                .withSku("ABC")
                .withQuantity(5)
                .then()
                .shouldSucceed()
                .and()
                .order("ORDER-1001")
                .shouldHaveSubtotalPrice(100.00);
    }

    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    @DataSource({"20.00", "5", "100.00"})
    @DataSource({"10.00", "3", "30.00"})
    @DataSource({"15.50", "4", "62.00"})
    @DataSource({"99.99", "1", "99.99"})
    void shouldPlaceOrderWithCorrectSubtotalPriceParameterized(String unitPrice, String quantity, String subtotalPrice) {
        scenario
                .given()
                .product()
                .withSku("ABC")
                .withUnitPrice(unitPrice)
                .when()
                .placeOrder()
                .withOrderNumber("ORDER-1001")
                .withSku("ABC")
                .withQuantity(quantity)
                .then()
                .shouldSucceed()
                .and()
                .order("ORDER-1001")
                .shouldHaveSubtotalPrice(subtotalPrice);
    }

    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    void shouldPlaceOrder() {
        scenario
                .given()
                .product()
                .withSku(SKU)
                .withUnitPrice(20.00)
                .when()
                .placeOrder()
                .withOrderNumber(ORDER_NUMBER)
                .withSku(SKU)
                .withQuantity(5)
                .withCountry("US")
                .then()
                .shouldSucceed()
                .expectOrderNumberPrefix("ORD-")
                .and()
                .order(ORDER_NUMBER)
                .hasSku(SKU)
                .hasQuantity(5)
                .hasCountry("US")
                .hasUnitPrice(20.00)
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

