package com.optivem.eshop.systemtest.core.dsl.shop.commands.execute;

import com.optivem.eshop.systemtest.core.drivers.system.ShopDriver;
import com.optivem.eshop.systemtest.core.dsl.commons.DslContext;
import com.optivem.eshop.systemtest.core.dsl.shop.commands.BaseShopCommand;

public class ViewOrder extends BaseShopCommand<Void> {
    public static final String COMMAND_NAME = "ViewOrder";

    private String orderNumberResultAlias;

    public ViewOrder(ShopDriver driver, DslContext context) {
        super(driver, context);
    }

    public ViewOrder orderNumber(String orderNumberResultAlias) {
        this.orderNumberResultAlias = orderNumberResultAlias;
        return this;
    }

    @Override
    public Void execute() {
        var orderNumber = context.results().getAliasValue(orderNumberResultAlias);

        var result = driver.viewOrder(orderNumber);

        context.results().registerResult(COMMAND_NAME, orderNumberResultAlias, result);
        return null;
    }
}

