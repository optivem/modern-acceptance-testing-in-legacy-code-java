package com.optivem.eshop.systemtest.dsl.core.app.shop.usecases;

import com.optivem.eshop.systemtest.driver.port.shop.ShopDriver;
import com.optivem.eshop.systemtest.dsl.core.app.shop.usecases.base.BaseShopCommand;
import com.optivem.eshop.systemtest.dsl.core.app.shared.UseCaseResult;
import com.optivem.eshop.systemtest.dsl.core.app.shared.UseCaseContext;
import com.optivem.eshop.systemtest.dsl.core.app.shared.VoidVerification;

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
    public UseCaseResult<Void, VoidVerification> execute() {
        var orderNumber = context.getResultValue(orderNumberResultAlias);
        var result = driver.cancelOrder(orderNumber);
        return new UseCaseResult<>(result, context, VoidVerification::new);
    }
}

