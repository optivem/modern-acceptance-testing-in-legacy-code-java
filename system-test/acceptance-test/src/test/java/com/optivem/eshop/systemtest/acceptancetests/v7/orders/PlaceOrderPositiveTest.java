package com.optivem.eshop.systemtest.acceptancetests.v7.orders;

import com.optivem.eshop.systemtest.acceptancetests.v7.base.BaseAcceptanceTest;
import com.optivem.eshop.systemtest.core.shop.ChannelType;
import com.optivem.eshop.systemtest.core.shop.commons.dtos.orders.OrderStatus;
import com.optivem.testing.annotations.Time;
import com.optivem.testing.channels.Channel;
import com.optivem.testing.channels.DataSource;
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
    void shouldCalculateBasePriceAsProductOfUnitPriceAndQuantity() {
        scenario
                .given().product().withUnitPrice(20.00)
                .when().placeOrder().withQuantity(5)
                .then().order().hasBasePrice(100.00);
    }

    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    @DataSource({"20.00", "5", "100.00"})
    @DataSource({"10.00", "3", "30.00"})
    @DataSource({"15.50", "4", "62.00"})
    @DataSource({"99.99", "1", "99.99"})
    void shouldPlaceOrderWithCorrectBasePriceParameterized(String unitPrice, String quantity, String basePrice) {
        scenario
                .given().product().withUnitPrice(unitPrice)
                .when().placeOrder().withQuantity(quantity)
                .then().order().hasBasePrice(basePrice);
    }

    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    void orderPrefixShouldBeORD() {
        scenario
                .when().placeOrder()
                .then().shouldSucceed().hasOrderNumberPrefix("ORD-")
                .and().order().hasOrderNumberPrefix("ORD-");
    }

    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    void discountRateShouldBeAppliedForCoupon() {
        scenario
                .given().coupon().withCouponCode("SUMMER2025").withDiscountRate(0.15)
                .when().placeOrder().withCouponCode("SUMMER2025")
                .then().order().hasAppliedCoupon("SUMMER2025")
                .hasDiscountRate(0.15);
    }

    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    void discountRateShouldBeNotAppliedWhenThereIsNoCoupon() {
        scenario
                .when().placeOrder().withCouponCode(null)
                .then().order().hasStatus(OrderStatus.PLACED)
                .hasAppliedCoupon(null)
                .hasDiscountRate(0.00)
                .hasDiscountAmount(0.00);
    }

    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    void subtotalPriceShouldBeCalculatedAsTheBasePriceMinusDiscountAmountWhenWeHaveCoupon() {
        scenario
                .given().coupon().withDiscountRate(0.15)
                .and().product().withUnitPrice(20.00)
                .when().placeOrder().withCouponCode().withQuantity(5)
                .then().order().hasAppliedCoupon()
                .hasDiscountRate(0.15)
                .hasBasePrice(100.00)
                .hasDiscountAmount(15.00)
                .hasSubtotalPrice(85.00);
    }

    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    void subtotalPriceShouldBeSameAsBasePriceWhenNoCoupon() {
        scenario
                .given().product().withUnitPrice(20.00)
                .when().placeOrder().withQuantity(5)
                .then().order()
                .hasBasePrice(100.00)
                .hasDiscountAmount(0.00)
                .hasSubtotalPrice(100.00);
    }

    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    @DataSource({"UK", "0.09"})
    @DataSource({"US", "0.20"})
    void correctTaxRateShouldBeUsedBasedOnCountry(String country, String taxRate) {
        scenario
                .given().taxRate().withCountry(country).withTaxRate(taxRate)
                .when().placeOrder().withCountry(country)
                .then().order().hasTaxRate(taxRate);
    }

    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    @DataSource({"UK", "0.09", "50.00", "4.50"})
    @DataSource({"US", "0.20", "100.00", "20.00"})
    void taxAmountShouldBeProductOfTaxRateAndBasePrice(String country, String taxRate, String basePrice, String expectedTaxAmount) {
        scenario
                .given().taxRate().withCountry(country).withTaxRate(taxRate)
                .and().product().withUnitPrice(basePrice)
                .when().placeOrder().withCountry(country).withQuantity(1)
                .then().order().hasTaxRate(taxRate)
                .hasBasePrice(basePrice)
                .hasTaxAmount(expectedTaxAmount);
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


