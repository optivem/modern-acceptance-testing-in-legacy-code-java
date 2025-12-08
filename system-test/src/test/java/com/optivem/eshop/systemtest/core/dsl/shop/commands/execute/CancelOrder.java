package com.optivem.eshop.systemtest.core.dsl.shop.commands.execute;

import com.optivem.eshop.systemtest.core.drivers.system.ShopDriver;
import com.optivem.eshop.systemtest.core.dsl.commons.commands.CommandResult;
import com.optivem.eshop.systemtest.core.dsl.commons.commands.VoidSuccessResult;
import com.optivem.eshop.systemtest.core.dsl.commons.context.DslContext;
import com.optivem.eshop.systemtest.core.dsl.shop.commands.BaseShopCommand;

public class CancelOrder extends BaseShopCommand<CommandResult<Void, VoidSuccessResult>> {
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
    public CommandResult<Void, VoidSuccessResult> execute() {
        var result = driver.cancelOrder(orderNumber);
        context.results().registerResult(COMMAND_NAME, result);
        return new CommandResult<>(result, context, VoidSuccessResult::new);
    }
}

