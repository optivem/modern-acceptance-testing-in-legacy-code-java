package com.optivem.eshop.systemtest.core.dsl.system.commands.execute;

import com.optivem.eshop.systemtest.core.drivers.system.ShopDriver;
import com.optivem.eshop.systemtest.core.dsl.commons.DslContext;
import com.optivem.eshop.systemtest.core.dsl.system.commands.BaseCommand;

public class ViewOrderCommand extends BaseCommand {
    private String orderNumber;

    public ViewOrderCommand(ShopDriver driver, DslContext context) {
        super(driver, context);
    }

    public ViewOrderCommand orderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
        return this;
    }

    @Override
    public void execute() {
        var result = driver.viewOrder(orderNumber);
        context.results().register("viewOrder", result);
    }
}

