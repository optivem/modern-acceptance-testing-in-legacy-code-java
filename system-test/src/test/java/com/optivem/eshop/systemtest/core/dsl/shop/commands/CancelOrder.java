package com.optivem.eshop.systemtest.core.dsl.shop.commands;

import com.optivem.eshop.systemtest.core.drivers.system.ShopDriver;
import com.optivem.eshop.systemtest.core.dsl.commons.commands.CommandResult;
import com.optivem.eshop.systemtest.core.dsl.commons.commands.VoidVerifications;
import com.optivem.eshop.systemtest.core.dsl.commons.context.DslContext;
import com.optivem.eshop.systemtest.core.dsl.shop.commands.base.BaseShopCommand;

public class CancelOrder extends BaseShopCommand<CommandResult<Void, VoidVerifications>> {
    private String orderNumberResultAlias;

    public CancelOrder(ShopDriver driver, DslContext context) {
        super(driver, context);
    }

    public CancelOrder orderNumber(String orderNumberResultAlias) {
        this.orderNumberResultAlias = orderNumberResultAlias;
        return this;
    }

    @Override
    public CommandResult<Void, VoidVerifications> execute() {
        var orderNumber = context.results().getAliasValue(orderNumberResultAlias);
        var result = driver.cancelOrder(orderNumber);
        return new CommandResult<>(result, context, VoidVerifications::new);
    }
}

