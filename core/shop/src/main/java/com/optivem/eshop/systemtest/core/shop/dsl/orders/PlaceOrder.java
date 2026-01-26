package com.optivem.eshop.systemtest.core.shop.dsl.orders;

import com.optivem.eshop.systemtest.core.shop.commons.dtos.orders.PlaceOrderRequest;
import com.optivem.eshop.systemtest.core.shop.commons.dtos.orders.PlaceOrderResponse;
import com.optivem.eshop.systemtest.core.shop.driver.ShopDriver;
import com.optivem.eshop.systemtest.core.shop.dsl.common.BaseShopCommand;
import com.optivem.eshop.systemtest.core.shop.dsl.common.ShopUseCaseResult;
import com.optivem.commons.dsl.UseCaseContext;

public class PlaceOrder extends BaseShopCommand<PlaceOrderResponse, PlaceOrderVerification> {
    private String orderNumberResultAlias;
    private String skuParamAlias;
    private String quantity;
    private String countryAlias;
    private String couponCodeAlias;

    public PlaceOrder(ShopDriver driver, UseCaseContext context) {
        super(driver, context);
    }

    public PlaceOrder orderNumber(String orderNumberResultAlias) {
        this.orderNumberResultAlias = orderNumberResultAlias;
        return this;
    }

    public PlaceOrder sku(String skuParamAlias) {
        this.skuParamAlias = skuParamAlias;
        return this;
    }

    public PlaceOrder quantity(String quantity) {
        this.quantity = quantity;
        return this;
    }

    public PlaceOrder quantity(int quantity) {
        return quantity(String.valueOf(quantity));
    }

    public PlaceOrder country(String countryAlias) {
        this.countryAlias = countryAlias;
        return this;
    }

    public PlaceOrder couponCode(String couponCodeAlias) {
        this.couponCodeAlias = couponCodeAlias;
        return this;
    }

    @Override
    public ShopUseCaseResult<PlaceOrderResponse, PlaceOrderVerification> execute() {
        var sku = context.getParamValue(skuParamAlias);
        var country = context.getParamValueOrLiteral(countryAlias);
        var couponCode = context.getParamValue(couponCodeAlias);

        var request = PlaceOrderRequest.builder()
                .sku(sku)
                .quantity(quantity)
                .country(country)
                .couponCode(couponCode)
                .build();

        var result = driver.orders().placeOrder(request);

        if(orderNumberResultAlias != null) {
            if(result.isSuccess()) {
                var orderNumber = result.getValue().getOrderNumber();
                context.setResultEntry(orderNumberResultAlias, orderNumber);
            } else {
                context.setResultEntryFailed(orderNumberResultAlias, result.getError().toString());
            }
        }

        return new ShopUseCaseResult<>(result, context, PlaceOrderVerification::new);
    }
}
