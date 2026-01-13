package com.optivem.eshop.systemtest.core.shop.dsl.commands;

import com.optivem.eshop.systemtest.core.shop.commons.dtos.orders.ViewOrderDetailsResponse;
import com.optivem.eshop.systemtest.core.shop.driver.ShopDriver;
import com.optivem.eshop.systemtest.core.shop.dsl.commands.base.BaseShopCommand;
import com.optivem.eshop.systemtest.core.shop.dsl.commands.base.ShopUseCaseResult;
import com.optivem.eshop.systemtest.core.shop.dsl.verifications.ViewOrderVerification;
import com.optivem.commons.dsl.UseCaseContext;

public class ViewOrder extends BaseShopCommand<ViewOrderDetailsResponse, ViewOrderVerification> {
    private String orderNumberResultAlias;

    public ViewOrder(ShopDriver driver, UseCaseContext context) {
        super(driver, context);
    }

    public ViewOrder orderNumber(String orderNumberResultAlias) {
        this.orderNumberResultAlias = orderNumberResultAlias;
        return this;
    }

    @Override
    public ShopUseCaseResult<ViewOrderDetailsResponse, ViewOrderVerification> execute() {
        var orderNumber = context.getResultValue(orderNumberResultAlias);

        var result = driver.orders().viewOrder(orderNumber);

        return new ShopUseCaseResult<>(result, context, ViewOrderVerification::new);
    }
}

