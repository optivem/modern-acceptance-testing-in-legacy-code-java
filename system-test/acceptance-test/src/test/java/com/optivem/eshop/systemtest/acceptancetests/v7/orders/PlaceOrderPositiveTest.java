package com.optivem.eshop.systemtest.acceptancetests.v7.orders;

import com.optivem.eshop.systemtest.acceptancetests.v7.base.BaseAcceptanceTest;
import com.optivem.eshop.systemtest.core.shop.ChannelType;
import com.optivem.eshop.systemtest.core.shop.commons.dtos.orders.OrderStatus;
import com.optivem.testing.channels.Channel;
import com.optivem.testing.channels.DataSource;
import com.optivem.testing.annotations.Time;
import org.junit.jupiter.api.Disabled;
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
    void discountRateShouldBeAppliedForCoupon() {
        scenario
                .given().coupon().withCouponCode("SUMMER2025").withDiscountRate(0.15)
                .when().placeOrder().withCouponCode("SUMMER2025")
                .then().order().hasStatus(OrderStatus.PLACED)
                    .hasAppliedCoupon("SUMMER2025")
                    .hasDiscountRate(0.15);
    }

//    @TestTemplate
//    @Time
//    @Channel({ChannelType.UI, ChannelType.API})
//    void discountRateShouldBe0percentWhenTimeBefore5pm() {
//        scenario
//                .given().clock().withTime("2025-12-24T10:02:00Z")
//                .when().placeOrder()
//                .then().order().hasDiscountRate(0);
//    }
//
//    @TestTemplate
//    @Time
//    @Channel({ChannelType.UI, ChannelType.API})
//    void discountRateShouldBe15percentWhenTimeAfter5pm() {
//        scenario
//                .given().clock().withTime("2025-12-24T17:01:00Z")
//                .when().placeOrder()
//                .then().order().hasDiscountRate(0.15);
//    }
//
//    @TestTemplate
//    @Time
//    @Channel({ChannelType.UI, ChannelType.API})
//    void discountAmountShouldBe0WhenTimeBefore5pm() {
//        scenario
//                .given().clock().withTime("2025-12-24T10:02:00Z")
//                .when().placeOrder()
//                .then().order().hasDiscountAmount(0);
//    }
//
//    @TestTemplate
//    @Time
//    @Channel({ChannelType.UI, ChannelType.API})
//    @DataSource({"20.00", "6", "18.00"})
//    @DataSource({"15.00", "4", "9.00"})
//    void discountAmountShouldBe15percentOfSubtotalPriceWhenTimeAfter5pm(String unitPrice, String quantity, String expectedDiscountAmount) {
//        scenario
//                .given().clock().withTime("2025-12-24T17:02:00Z")
//                .and().product().withUnitPrice(unitPrice)
//                .when().placeOrder().withQuantity(quantity)
//                .then().order().hasDiscountAmount(expectedDiscountAmount);
//    }
//
//    @TestTemplate
//    @Channel({ChannelType.UI, ChannelType.API})
//    @DataSource({"20.00", "6", "120.00", "18.00", "102.00"})
//    @DataSource({"15.00", "4", "60.00", "9.00", "51.00"})
//    void subtotalPriceShouldBeBasePriceMinusDiscountAmountWhenTimeAfter5pm(String unitPrice, String quantity, String expectedBasePrice, String expectedDiscountAmount, String expectedSubtotalPrice) {
//        scenario
//                .given().clock().withTime("2025-12-24T17:02:00Z")
//                .and().product().withUnitPrice(unitPrice)
//                .when().placeOrder().withQuantity(quantity)
//                .then().order().hasBasePrice(expectedBasePrice)
//                .hasDiscountAmount(expectedDiscountAmount)
//                .shouldHaveSubtotalPrice(expectedSubtotalPrice);
//    }

    @TestTemplate
    @Time
    @Channel({ChannelType.UI, ChannelType.API})
    @DataSource({"UK", "0.09"})
    @DataSource({"US", "0.20"})
    void correctTaxRateShouldBeUsedBasedOnCountry(String country, String taxRate) {
        scenario
                .given().taxRate().withCountry(country).withTaxRate(taxRate)
                .when().placeOrder().withCountry(country)
                .then().order().hasTaxRate(taxRate);
    }

//    // TODO: VJ: Introduce coupon codes, rather than time based
//    @Disabled
//    @TestTemplate
//    @Time
//    @Channel({ChannelType.UI, ChannelType.API})
//    @DataSource({"0.10", "100.00", "10.00"})
//    @DataSource({"0.15", "100.00", "15.00" })
//    void taxAmountShouldBeCalculatedAsProductOfSubtotalPriceAndTaxRate(String taxRate, String subtotalPrice, String expectedTaxAmount) {
//        scenario
//                .given().taxRate().withTaxRate(taxRate)
//                .and().product().withUnitPrice(subtotalPrice)
//                .when().placeOrder().withQuantity(1)
//                .then().order().hasTaxRate(taxRate).hasTaxAmount(expectedTaxAmount);
//    }



    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    void discountAmountShouldBeGreaterThanOrEqualToZero() {
        scenario
                .when().placeOrder()
                .then().order().hasDiscountAmountGreaterThanOrEqualToZero();
    }

    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    void subtotalPriceShouldBeGreaterThanZero() {
        scenario
                .when().placeOrder()
                .then().order().hasSubtotalPriceGreaterThanZero();
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

