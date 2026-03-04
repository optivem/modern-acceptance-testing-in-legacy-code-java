package com.optivem.eshop.systemtest.dsl.core.app.shop.usecases;

import com.optivem.eshop.systemtest.driver.port.shop.dtos.ViewOrderResponse;
import com.optivem.eshop.systemtest.driver.port.shop.ShopDriver;
import com.optivem.eshop.systemtest.dsl.core.app.shop.usecases.base.BaseShopUseCase;
import com.optivem.eshop.systemtest.dsl.core.app.UseCaseResult;
import com.optivem.eshop.systemtest.dsl.core.app.UseCaseContext;

public class ViewOrder extends BaseShopUseCase<ViewOrderResponse, ViewOrderVerification> {
    private String orderNumberResultAlias;

    public ViewOrder(ShopDriver driver, UseCaseContext context) {
        super(driver, context);
    }

    public ViewOrder orderNumber(String orderNumberResultAlias) {
        this.orderNumberResultAlias = orderNumberResultAlias;
        return this;
    }

    @Override
    public UseCaseResult<ViewOrderResponse, ViewOrderVerification> execute() {
        var orderNumber = context.getResultValue(orderNumberResultAlias);

        var result = driver.viewOrder(orderNumber);

        return new UseCaseResult<>(result, context, ViewOrderVerification::new);
    }
}

