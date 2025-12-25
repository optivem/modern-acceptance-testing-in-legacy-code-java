package com.optivem.eshop.systemtest.acceptancetests.v7;

import com.optivem.eshop.systemtest.acceptancetests.v7.base.BaseAcceptanceTest;
import com.optivem.eshop.systemtest.core.shop.ChannelType;
import com.optivem.eshop.systemtest.core.shop.client.dtos.enums.OrderStatus;
import com.optivem.testing.channels.Channel;
import org.junit.jupiter.api.TestTemplate;

import static com.optivem.eshop.systemtest.acceptancetests.commons.constants.Defaults.ORDER_NUMBER;
import static com.optivem.eshop.systemtest.acceptancetests.commons.constants.Defaults.SKU;

public class ViewOrderPositiveTest extends BaseAcceptanceTest {

    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    void shouldViewPlacedOrder() {
        scenario
                .given()
                .product()
                .withSku(SKU)
                .withUnitPrice(25.00)
                .and().taxRate().withCountry("US").withTaxRate(0.0)
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
                .hasUnitPrice(25.00)
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

