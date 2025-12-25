package com.optivem.eshop.systemtest.acceptancetests.v7;

import com.optivem.eshop.systemtest.acceptancetests.v7.base.BaseAcceptanceTest;
import com.optivem.eshop.systemtest.core.shop.ChannelType;
import com.optivem.eshop.systemtest.core.shop.client.dtos.enums.OrderStatus;
import com.optivem.testing.channels.Channel;
import com.optivem.testing.channels.DataSource;
import org.junit.jupiter.api.TestTemplate;

import static com.optivem.eshop.systemtest.acceptancetests.commons.constants.Defaults.ORDER_NUMBER;
import static com.optivem.eshop.systemtest.acceptancetests.commons.constants.Defaults.SKU;

public class PlaceOrderPositiveTest extends BaseAcceptanceTest {
    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    void shouldCalculateOriginalPrice() {
        scenario
                .given()
                .product().withSku("ABC").withUnitPrice(20.00)
                .and().taxRate().withCountry("US").withTaxRate(0.0)
                .when().placeOrder().withOrderNumber("ORDER-1001").withSku("ABC").withQuantity(5).withCountry("US")
                .then().shouldSucceed()
                .and().order("ORDER-1001").shouldHaveOriginalPrice(100.00);
    }

    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    void shouldPlaceOrderWithCorrectOriginalPrice() {
        scenario
                .given()
                .product()
                .withSku("ABC")
                .withUnitPrice(20.00)
                .and().taxRate().withCountry("US").withTaxRate(0.0)
                .when()
                .placeOrder()
                .withOrderNumber("ORDER-1001")
                .withSku("ABC")
                .withQuantity(5)
                .withCountry("US")
                .then()
                .shouldSucceed()
                .and()
                .order("ORDER-1001")
                .shouldHaveOriginalPrice(100.00);
    }

    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    @DataSource({"20.00", "5", "100.00"})
    @DataSource({"10.00", "3", "30.00"})
    @DataSource({"15.50", "4", "62.00"})
    @DataSource({"99.99", "1", "99.99"})
    void shouldPlaceOrderWithCorrectOriginalPriceParameterized(String unitPrice, String quantity, String originalPrice) {
        scenario
                .given()
                .product()
                .withSku("ABC")
                .withUnitPrice(unitPrice)
                .and().taxRate().withCountry("US").withTaxRate(0.0)
                .when()
                .placeOrder()
                .withOrderNumber("ORDER-1001")
                .withSku("ABC")
                .withQuantity(quantity)
                .withCountry("US")
                .then()
                .shouldSucceed()
                .and()
                .order("ORDER-1001")
                .shouldHaveOriginalPrice(originalPrice);
    }

    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    void shouldPlaceOrder() {
        scenario
                .given()
                .product()
                .withSku(SKU)
                .withUnitPrice(20.00)
                .and().taxRate().withCountry("US").withTaxRate(0.05)
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
                .hasUnitPrice(20.00)
                .shouldHaveOriginalPrice(100.00)
                .hasStatus(OrderStatus.PLACED)
                .hasDiscountRateGreaterThanOrEqualToZero()
                .hasDiscountAmountGreaterThanOrEqualToZero()
                .hasSubtotalPriceGreaterThanZero()
                .hasTaxRateGreaterThanOrEqualToZero()
                .hasTaxAmountGreaterThanOrEqualToZero()
                .hasTotalPriceGreaterThanZero();
    }
}

