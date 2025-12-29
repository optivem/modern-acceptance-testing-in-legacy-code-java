package com.optivem.eshop.systemtest.acceptancetests.v7;

import com.optivem.eshop.systemtest.acceptancetests.v7.base.BaseAcceptanceTest;
import com.optivem.eshop.systemtest.core.shop.ChannelType;
import com.optivem.testing.channels.Channel;
import com.optivem.testing.channels.DataSource;
import com.optivem.testing.annotations.Time;
import org.junit.jupiter.api.TestTemplate;

public class PlaceOrderPositiveTest extends BaseAcceptanceTest {

    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    void shouldBeAbleToPlaceOrderForValidInput() {
        scenario
                .given().product().withSku("ABC").withUnitPrice(20.00)
                .and().taxRate().withCountry("US").withTaxRate(0.10)
                .when().placeOrder().withSku("ABC").withQuantity(5).withCountry("US")
                .then().shouldSucceed();
    }

    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    void shouldCalculateSubtotalPriceAsProductOfUnitPriceAndQuantity() {
        scenario
                .given().product().withUnitPrice(20.00)
                .when().placeOrder().withQuantity(5)
                .then().order().shouldHaveSubtotalPrice(100.00);
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
                .then().order().shouldHaveSubtotalPrice(subtotalPrice);
    }

    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    void orderPrefixShouldBeORD() {
        scenario
                .when().placeOrder()
                .then().shouldSucceed().expectOrderNumberPrefix("ORD-")
                .and().order().expectOrderNumberPrefix("ORD-");
    }

    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    @Time
    void discountRateShouldBe0percentWhenTimeBefore5pm() {
        scenario
                .given().clock().withTime("2025-12-24T10:02:00Z")
                .when().placeOrder()
                .then().order().hasDiscountRate(0);
    }

    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    @Time
    void discountRateShouldBe15percentWhenTimeAfter5pm() {
        scenario
                .given().clock().withTime("2025-12-24T17:01:00Z")
                .when().placeOrder()
                .then().order().hasDiscountRate(0.15);
    }

    // TODO: VJ: Continue here

    // @TestTemplate
    // @Channel({ChannelType.UI, ChannelType.API})
    // @Time
    // void discountAmountShouldBe0percentWhenTimeBefore5pm() {
    //     scenario
    //             .given().clock().withTime("2025-12-24T10:02:00Z")
    //             .when().placeOrder()
    //             .then().order().hasDiscountAmount(0);
    // }

    // @TestTemplate
    // @Channel({ChannelType.UI, ChannelType.API})
    // @Time
    // void discountAmountShouldBe15percentWhenTimeAfter5pm() {
    //     scenario
    //             .given().clock().withTime("2025-12-24T17:01:00Z")
    //             .when().placeOrder()
    //             .then().order().hasDiscountAmount(0.15);
    // }


    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    void discountAmountShouldBeGreaterThanOrEqualToZero() {
        scenario
                .when().placeOrder()
                .then().order().hasDiscountAmountGreaterThanOrEqualToZero();
    }

    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    void preTaxTotalShouldBeGreaterThanZero() {
        scenario
                .when().placeOrder()
                .then().order().hasPreTaxTotalGreaterThanZero();
    }

    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    void taxRateShouldBeGreaterThanOrEqualToZero() {
        scenario
                .when().placeOrder()
                .then().order().hasTaxRateGreaterThanOrEqualToZero();
    }

    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    void taxAmountShouldBeGreaterThanOrEqualToZero() {
        scenario
                .when().placeOrder()
                .then().order().hasTaxAmountGreaterThanOrEqualToZero();
    }

    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    void totalPriceShouldBeGreaterThanOrEqualToZero() {
        scenario
                .when().placeOrder()
                .then().order().hasTotalPriceGreaterThanZero();
    }




}

