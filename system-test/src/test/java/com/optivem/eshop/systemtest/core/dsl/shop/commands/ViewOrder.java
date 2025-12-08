package com.optivem.eshop.systemtest.core.dsl.shop.commands;

import com.optivem.eshop.systemtest.core.drivers.system.ShopDriver;
import com.optivem.eshop.systemtest.core.drivers.system.commons.dtos.GetOrderResponse;
import com.optivem.eshop.systemtest.core.dsl.commons.commands.CommandResult;
import com.optivem.eshop.systemtest.core.dsl.commons.context.Context;
import com.optivem.eshop.systemtest.core.dsl.shop.commands.base.BaseShopCommand;

public class ViewOrder extends BaseShopCommand<GetOrderResponse, ViewOrderVerification> {
    private String orderNumberResultAlias;

    public ViewOrder(ShopDriver driver, Context context) {
        super(driver, context);
    }

    public ViewOrder orderNumber(String orderNumberResultAlias) {
        this.orderNumberResultAlias = orderNumberResultAlias;
        return this;
    }

    @Override
    public CommandResult<GetOrderResponse, ViewOrderVerification> execute() {
        var orderNumber = context.getResultValue(orderNumberResultAlias);

        var result = driver.viewOrder(orderNumber);

        return new CommandResult<>(result, context, ViewOrderVerification::new);
    }
}

