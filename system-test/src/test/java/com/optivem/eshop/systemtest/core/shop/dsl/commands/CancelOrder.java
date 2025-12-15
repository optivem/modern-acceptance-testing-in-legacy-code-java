package com.optivem.eshop.systemtest.core.shop.dsl.commands;

import com.optivem.eshop.systemtest.core.shop.driver.ShopDriver;
import com.optivem.testing.dsl.UseCaseResult;
import com.optivem.testing.dsl.UseCaseVoidSuccessVerification;
import com.optivem.testing.dsl.UseCaseContext;
import com.optivem.eshop.systemtest.core.shop.dsl.commands.base.BaseShopCommand;

public class CancelOrder extends BaseShopCommand<Void, UseCaseVoidSuccessVerification> {
    private String orderNumberResultAlias;

    public CancelOrder(ShopDriver driver, UseCaseContext context) {
        super(driver, context);
    }

    public CancelOrder orderNumber(String orderNumberResultAlias) {
        this.orderNumberResultAlias = orderNumberResultAlias;
        return this;
    }

    @Override
    public UseCaseResult<Void, UseCaseVoidSuccessVerification> execute() {
        var orderNumber = context.getResultValue(orderNumberResultAlias);
        var result = driver.cancelOrder(orderNumber);
        return new UseCaseResult<>(result, context, UseCaseVoidSuccessVerification::new);
    }
}

