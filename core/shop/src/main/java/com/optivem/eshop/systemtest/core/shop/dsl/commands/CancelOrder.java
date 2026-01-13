package com.optivem.eshop.systemtest.core.shop.dsl.commands;

import com.optivem.eshop.systemtest.core.shop.driver.ShopDriver;
import com.optivem.eshop.systemtest.core.shop.dsl.commands.base.BaseShopCommand;
import com.optivem.eshop.systemtest.core.shop.dsl.commands.base.ShopUseCaseResult;
import com.optivem.commons.dsl.UseCaseContext;
import com.optivem.commons.dsl.VoidVerification;

public class CancelOrder extends BaseShopCommand<Void, VoidVerification<UseCaseContext>> {
    private String orderNumberResultAlias;

    public CancelOrder(ShopDriver driver, UseCaseContext context) {
        super(driver, context);
    }

    public CancelOrder orderNumber(String orderNumberResultAlias) {
        this.orderNumberResultAlias = orderNumberResultAlias;
        return this;
    }

    @Override
    public ShopUseCaseResult<Void, VoidVerification<UseCaseContext>> execute() {
        var orderNumber = context.getResultValue(orderNumberResultAlias);
        var result = driver.orders().cancelOrder(orderNumber);
        return new ShopUseCaseResult<>(result, context, VoidVerification::new);
    }
}

