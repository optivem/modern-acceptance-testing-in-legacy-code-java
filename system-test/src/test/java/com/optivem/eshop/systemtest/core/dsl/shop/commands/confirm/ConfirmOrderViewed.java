package com.optivem.eshop.systemtest.core.dsl.shop.commands.confirm;

import com.optivem.eshop.systemtest.core.drivers.system.ShopDriver;
import com.optivem.eshop.systemtest.core.drivers.system.commons.dtos.GetOrderResponse;
import com.optivem.eshop.systemtest.core.drivers.system.commons.enums.OrderStatus;
import com.optivem.eshop.systemtest.core.dsl.commons.DslContext;
import com.optivem.eshop.systemtest.core.dsl.shop.commands.BaseShopCommand;
import com.optivem.eshop.systemtest.core.dsl.shop.commands.execute.ViewOrder;

import java.math.BigDecimal;

import static com.optivem.testing.assertions.ResultAssert.assertThatResult;
import static org.assertj.core.api.Assertions.assertThat;

public class ConfirmOrderViewed extends BaseShopCommand<Void> {
    private String orderNumberResultAlias;
    private String skuParamAlias;
    private Integer expectedQuantity;
    private String expectedCountry;
    private BigDecimal expectedUnitPrice;
    private BigDecimal expectedOriginalPrice;
    private OrderStatus expectedStatus;

    public ConfirmOrderViewed(ShopDriver driver, DslContext context) {
        super(driver, context);
    }

    public ConfirmOrderViewed orderNumber(String orderNumberResultAlias) {
        this.orderNumberResultAlias = orderNumberResultAlias;
        return this;
    }

    public ConfirmOrderViewed sku(String skuParamAlias) {
        this.skuParamAlias = skuParamAlias;
        return this;
    }

    public ConfirmOrderViewed quantity(int expectedQuantity) {
        this.expectedQuantity = expectedQuantity;
        return this;
    }

    public ConfirmOrderViewed country(String expectedCountry) {
        this.expectedCountry = expectedCountry;
        return this;
    }

    public ConfirmOrderViewed unitPrice(String expectedUnitPrice) {
        this.expectedUnitPrice = new BigDecimal(expectedUnitPrice);
        return this;
    }

    public ConfirmOrderViewed originalPrice(String expectedOriginalPrice) {
        this.expectedOriginalPrice = new BigDecimal(expectedOriginalPrice);
        return this;
    }

    public ConfirmOrderViewed status(OrderStatus expectedStatus) {
        this.expectedStatus = expectedStatus;
        return this;
    }

//    var discountRate = viewOrderResponse.getDiscountRate();
//    var discountAmount = viewOrderResponse.getDiscountAmount();
//    var subtotalPrice = viewOrderResponse.getSubtotalPrice();
//
//    assertThat(discountRate)
//                .isGreaterThanOrEqualTo(BigDecimal.ZERO);
//
//    assertThat(discountAmount)
//                .isGreaterThanOrEqualTo(BigDecimal.ZERO);
//
//    assertThat(subtotalPrice)
//                .isGreaterThan(BigDecimal.ZERO);
//
//
//    var taxRate = viewOrderResponse.getTaxRate();
//    var taxAmount = viewOrderResponse.getTaxAmount();
//    var totalPrice = viewOrderResponse.getTotalPrice();
//
//    assertThat(taxRate)
//                .withFailMessage("Tax rate should be non-negative")
//                .isGreaterThanOrEqualTo(BigDecimal.ZERO);
//
//    assertThat(taxAmount)
//                .withFailMessage("Tax amount should be non-negative")
//                .isGreaterThanOrEqualTo(BigDecimal.ZERO);
//
//    assertThat(totalPrice)
//                .withFailMessage("Total price should be positive")
//                .isGreaterThan(BigDecimal.ZERO);

    @Override
    public Void execute() {
        if(orderNumberResultAlias == null) {
            throw new IllegalStateException("orderNumberResultAlias must be provided");
        }

        var result = context.results().getResult(ViewOrder.COMMAND_NAME, orderNumberResultAlias, GetOrderResponse.class);
        assertThatResult(result).isSuccess();

        var viewOrderResponse = result.getValue();

        var expectedOrderNumber = context.results().getAliasValue(orderNumberResultAlias);
        assertThat(viewOrderResponse.getOrderNumber()).isEqualTo(expectedOrderNumber);

        if (skuParamAlias != null) {
            var expectedSku = context.params().getOrGenerateAliasValue(skuParamAlias);
            assertThat(viewOrderResponse.getSku()).isEqualTo(expectedSku);
        }

        if (expectedQuantity != null) {
            assertThat(viewOrderResponse.getQuantity()).isEqualTo(expectedQuantity);
        }

        if (expectedCountry != null) {
            assertThat(viewOrderResponse.getCountry()).isEqualTo(expectedCountry);
        }

        if (expectedUnitPrice != null) {
            assertThat(viewOrderResponse.getUnitPrice()).isEqualTo(expectedUnitPrice);
        }

        if (expectedOriginalPrice != null) {
            assertThat(viewOrderResponse.getOriginalPrice()).isEqualTo(expectedOriginalPrice);
        }

        if (expectedStatus != null) {
            assertThat(viewOrderResponse.getStatus()).isEqualTo(expectedStatus);
        }

        return null;
    }
}

