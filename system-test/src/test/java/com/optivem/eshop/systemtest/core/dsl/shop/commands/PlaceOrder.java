package com.optivem.eshop.systemtest.core.dsl.shop.commands;

import com.optivem.eshop.systemtest.core.drivers.system.ShopDriver;
import com.optivem.eshop.systemtest.core.drivers.system.commons.dtos.PlaceOrderRequest;
import com.optivem.eshop.systemtest.core.drivers.system.commons.dtos.PlaceOrderResponse;
import com.optivem.eshop.systemtest.core.dsl.commons.commands.CommandResult;
import com.optivem.eshop.systemtest.core.dsl.commons.context.DslContext;
import com.optivem.eshop.systemtest.core.dsl.shop.commands.base.BaseShopCommand;

public class PlaceOrder extends BaseShopCommand<CommandResult<PlaceOrderResponse, PlaceOrderVerifications>> {
    private String orderNumberResultAlias;
    private String skuParamAlias;
    private String quantity;
    private String country;

    public PlaceOrder(ShopDriver driver, DslContext context) {
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

    public PlaceOrder country(String country) {
        this.country = country;
        return this;
    }

    @Override
    public CommandResult<PlaceOrderResponse, PlaceOrderVerifications> execute() {
        var sku = context.params().getOrGenerateAliasValue(skuParamAlias);

        var request = PlaceOrderRequest.builder()
                .sku(sku)
                .quantity(quantity)
                .country(country)
                .build();
        var result = driver.placeOrder(request);

        if (result.isSuccess()) {
            var orderNumber = result.getValue().getOrderNumber();
            context.results().setAliasValue(orderNumberResultAlias, orderNumber);
        }

        return new CommandResult<>(result, context, PlaceOrderVerifications::new);
    }
}
