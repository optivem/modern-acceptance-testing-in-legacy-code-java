package com.optivem.eshop.systemtest.core.shop.dsl.usecases.orders;

import com.optivem.eshop.systemtest.core.shop.commons.dtos.orders.ViewOrderResponse;
import com.optivem.eshop.systemtest.core.shop.driver.ShopDriver;
import com.optivem.eshop.systemtest.core.shop.dsl.usecases.base.BaseShopCommand;
import com.optivem.eshop.systemtest.core.shop.dsl.usecases.base.ShopUseCaseResult;
import com.optivem.commons.dsl.UseCaseContext;

public class ViewOrder extends BaseShopCommand<ViewOrderResponse, ViewOrderVerification> {
    private String orderNumberResultAlias;

    public ViewOrder(ShopDriver driver, UseCaseContext context) {
        super(driver, context);
    }

    public ViewOrder orderNumber(String orderNumberResultAlias) {
        this.orderNumberResultAlias = orderNumberResultAlias;
        return this;
    }

    @Override
    public ShopUseCaseResult<ViewOrderResponse, ViewOrderVerification> execute() {
        var orderNumber = context.getResultValue(orderNumberResultAlias);

        var result = driver.viewOrder(orderNumber);

        return new ShopUseCaseResult<>(result, context, ViewOrderVerification::new);
    }
}
