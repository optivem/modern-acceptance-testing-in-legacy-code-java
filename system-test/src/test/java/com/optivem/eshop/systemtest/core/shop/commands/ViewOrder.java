package com.optivem.eshop.systemtest.core.shop.commands;

import com.optivem.eshop.systemtest.core.shop.driver.ShopDriver;
import com.optivem.eshop.systemtest.core.shop.dtos.GetOrderResponse;
import com.optivem.testing.dsl.CommandResult;
import com.optivem.testing.dsl.Context;
import com.optivem.eshop.systemtest.core.shop.commands.base.BaseShopCommand;
import com.optivem.eshop.systemtest.core.shop.verifications.ViewOrderVerification;

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

