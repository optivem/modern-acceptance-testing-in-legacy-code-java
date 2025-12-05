package com.optivem.eshop.systemtest.core.dsl.system.commands.execute;

import com.optivem.eshop.systemtest.core.drivers.system.ShopDriver;
import com.optivem.eshop.systemtest.core.dsl.commons.DslContext;
import com.optivem.eshop.systemtest.core.dsl.system.commands.BaseCommand;

public class CancelOrderCommand extends BaseCommand {
    private String orderNumber;

    public CancelOrderCommand(ShopDriver driver, DslContext context) {
        super(driver, context);
    }

    public CancelOrderCommand orderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
        return this;
    }

    @Override
    public void execute() {
        var result = driver.cancelOrder(orderNumber);
        context.results().register("cancelOrder", result);
    }
}

