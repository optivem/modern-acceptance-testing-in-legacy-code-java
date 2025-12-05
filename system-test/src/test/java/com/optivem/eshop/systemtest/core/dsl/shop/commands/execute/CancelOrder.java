package com.optivem.eshop.systemtest.core.dsl.shop.commands.execute;

import com.optivem.eshop.systemtest.core.drivers.system.ShopDriver;
import com.optivem.eshop.systemtest.core.dsl.commons.DslContext;
import com.optivem.eshop.systemtest.core.dsl.shop.commands.BaseShopCommand;

public class CancelOrder extends BaseShopCommand<Void> {
    public static final String COMMAND_NAME = "CancelOrder";

    private String orderNumber;

    public CancelOrder(ShopDriver driver, DslContext context) {
        super(driver, context);
    }

    public CancelOrder orderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
        return this;
    }

    @Override
    public Void execute() {
        var result = driver.cancelOrder(orderNumber);
        context.results().registerResult(COMMAND_NAME, result);
        return null;
    }
}

