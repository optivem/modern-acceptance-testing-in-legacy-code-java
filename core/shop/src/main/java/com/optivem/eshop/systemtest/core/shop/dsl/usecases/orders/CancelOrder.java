package com.optivem.eshop.systemtest.core.shop.dsl.usecases.orders;

import com.optivem.eshop.systemtest.core.shop.driver.ShopDriver;
import com.optivem.eshop.systemtest.core.shop.dsl.usecases.base.BaseShopCommand;
import com.optivem.eshop.systemtest.core.shop.dsl.usecases.base.ShopUseCaseResult;
import com.optivem.commons.dsl.UseCaseContext;
import com.optivem.commons.dsl.VoidVerification;

public class CancelOrder extends BaseShopCommand<Void, VoidVerification> {
    private String orderNumberResultAlias;

    public CancelOrder(ShopDriver driver, UseCaseContext context) {
        super(driver, context);
    }

    public CancelOrder orderNumber(String orderNumberResultAlias) {
        this.orderNumberResultAlias = orderNumberResultAlias;
        return this;
    }

    @Override
    public ShopUseCaseResult<Void, VoidVerification> execute() {
        var orderNumber = context.getResultValue(orderNumberResultAlias);
        var result = driver.cancelOrder(orderNumber);
        return new ShopUseCaseResult<>(result, context, VoidVerification::new);
    }
}
