package com.optivem.eshop.systemtest.core.shop.commands;

import com.optivem.eshop.systemtest.core.shop.driver.ShopDriver;
import com.optivem.testing.dsl.CommandResult;
import com.optivem.testing.dsl.VoidVerification;
import com.optivem.testing.dsl.Context;
import com.optivem.eshop.systemtest.core.shop.commands.base.BaseShopCommand;

public class CancelOrder extends BaseShopCommand<Void, VoidVerification> {
    private String orderNumberResultAlias;

    public CancelOrder(ShopDriver driver, Context context) {
        super(driver, context);
    }

    public CancelOrder orderNumber(String orderNumberResultAlias) {
        this.orderNumberResultAlias = orderNumberResultAlias;
        return this;
    }

    @Override
    public CommandResult<Void, VoidVerification> execute() {
        var orderNumber = context.getResultValue(orderNumberResultAlias);
        var result = driver.cancelOrder(orderNumber);
        return new CommandResult<>(result, context, VoidVerification::new);
    }
}

