package com.optivem.eshop.systemtest.core.shop.dsl.commands;

import com.optivem.eshop.systemtest.core.shop.client.dtos.PlaceOrderRequest;
import com.optivem.eshop.systemtest.core.shop.client.dtos.PlaceOrderResponse;
import com.optivem.eshop.systemtest.core.shop.driver.ShopDriver;
import com.optivem.eshop.systemtest.core.shop.dsl.commands.base.BaseShopCommand;
import com.optivem.eshop.systemtest.core.shop.dsl.commands.base.ShopUseCaseResult;
import com.optivem.eshop.systemtest.core.shop.dsl.verifications.PlaceOrderVerification;
import com.optivem.testing.dsl.UseCaseContext;

public class PlaceOrder extends BaseShopCommand<PlaceOrderResponse, PlaceOrderVerification> {
    private static final String DEFAULT_SKU = "DEFAULT-SKU";
    private static final int DEFAULT_QUANTITY = 1;
    private static final String DEFAULT_COUNTRY = "US";
    private String orderNumberResultAlias;
    private String skuParamAlias;
    private String quantity;
    private String country;

    public PlaceOrder(ShopDriver driver, UseCaseContext context) {
        super(driver, context);

        sku(DEFAULT_SKU);
        quantity(DEFAULT_QUANTITY);
        country(DEFAULT_COUNTRY);
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

    public PlaceOrder country(String country) {
        this.country = country;
        return this;
    }

    @Override
    public ShopUseCaseResult<PlaceOrderResponse, PlaceOrderVerification> execute() {
        var sku = context.getParamValue(skuParamAlias);

        var request = PlaceOrderRequest.builder()
                .sku(sku)
                .quantity(quantity)
                .country(country)
                .build();
        var result = driver.placeOrder(request);

        if (result.isSuccess() && orderNumberResultAlias != null) {
            var orderNumber = result.getValue().getOrderNumber();
            context.setResultEntry(orderNumberResultAlias, orderNumber);
        }

        return new ShopUseCaseResult<>(result, context, PlaceOrderVerification::new);
    }
}
