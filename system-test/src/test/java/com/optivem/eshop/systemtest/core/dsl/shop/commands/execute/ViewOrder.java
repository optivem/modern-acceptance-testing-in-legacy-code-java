package com.optivem.eshop.systemtest.core.dsl.shop.commands.execute;

import com.optivem.eshop.systemtest.core.drivers.system.ShopDriver;
import com.optivem.eshop.systemtest.core.drivers.system.commons.dtos.GetOrderResponse;
import com.optivem.eshop.systemtest.core.dsl.commons.DslContext;
import com.optivem.eshop.systemtest.core.dsl.shop.commands.BaseShopCommand;

public class ViewOrder extends BaseShopCommand<CommandResult<GetOrderResponse, ViewOrderSuccessResult>> {
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
    public CommandResult<GetOrderResponse, ViewOrderSuccessResult> execute() {
        var orderNumber = context.results().getAliasValue(orderNumberResultAlias);

        var result = driver.viewOrder(orderNumber);

        context.results().registerResult(COMMAND_NAME, orderNumberResultAlias, result);
        return new CommandResult<>(result, context, ViewOrderSuccessResult::new);
    }
}

