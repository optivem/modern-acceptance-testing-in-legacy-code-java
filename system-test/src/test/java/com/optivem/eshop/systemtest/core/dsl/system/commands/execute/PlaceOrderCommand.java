package com.optivem.eshop.systemtest.core.dsl.system.commands.execute;

import com.optivem.eshop.systemtest.core.drivers.system.ShopDriver;
import com.optivem.eshop.systemtest.core.drivers.system.commons.dtos.PlaceOrderRequest;
import com.optivem.eshop.systemtest.core.dsl.commons.DslContext;
import com.optivem.eshop.systemtest.core.dsl.system.commands.BaseShopCommand;

public class PlaceOrderCommand extends BaseShopCommand {
    private String orderNumberResultAlias;
    private String skuParamAlias;
    private String quantity;
    private String country;

    public PlaceOrderCommand(ShopDriver driver, DslContext context) {
        super(driver, context);
    }

    public PlaceOrderCommand orderNumber(String orderNumberResultAlias) {
        this.orderNumberResultAlias = orderNumberResultAlias;
        return this;
    }

    public PlaceOrderCommand sku(String skuParamAlias) {
        this.skuParamAlias = skuParamAlias;
        return this;
    }

    public PlaceOrderCommand quantity(String quantity) {
        this.quantity = quantity;
        return this;
    }

    public PlaceOrderCommand country(String country) {
        this.country = country;
        return this;
    }

    @Override
    public void execute() {
        var sku = context.params().alias(skuParamAlias);

        var request = PlaceOrderRequest.builder()
                .sku(sku)
                .quantity(quantity)
                .country(country)
                .build();
        var result = driver.placeOrder(request);

        context.results().register("placeOrder", orderNumberResultAlias, result);
    }
}
