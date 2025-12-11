package com.optivem.eshop.systemtest.dsl.shop.commands;

import com.optivem.eshop.systemtest.dsl.shop.drivers.ShopDriver;
import com.optivem.eshop.systemtest.dsl.shop.dtos.PlaceOrderRequest;
import com.optivem.eshop.systemtest.dsl.shop.dtos.PlaceOrderResponse;
import com.optivem.testing.dsl.CommandResult;
import com.optivem.testing.dsl.Context;
import com.optivem.eshop.systemtest.dsl.shop.commands.base.BaseShopCommand;
import com.optivem.eshop.systemtest.dsl.shop.verifications.PlaceOrderVerification;

public class PlaceOrder extends BaseShopCommand<PlaceOrderResponse, PlaceOrderVerification> {
    private String orderNumberResultAlias;
    private String skuParamAlias;
    private String quantity;
    private String country;

    private static final String DEFAULT_SKU = "DEFAULT-SKU";
    private static final int DEFAULT_QUANTITY = 1;
    private static final String DEFAULT_COUNTRY = "US";

    public PlaceOrder(ShopDriver driver, Context context) {
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
    public CommandResult<PlaceOrderResponse, PlaceOrderVerification> execute() {
        var sku = context.getParamValue(skuParamAlias);

        var request = PlaceOrderRequest.builder()
                .sku(sku)
                .quantity(quantity)
                .country(country)
                .build();
        var result = driver.placeOrder(request);

        if (result.isSuccess()) {
            var orderNumber = result.getValue().getOrderNumber();
            context.setResultEntry(orderNumberResultAlias, orderNumber);
        }

        return new CommandResult<>(result, context, PlaceOrderVerification::new);
    }
}
