package com.optivem.eshop.systemtest.dsl.core.app.shop.usecases.base;

import com.optivem.eshop.systemtest.driver.port.shop.ShopDriver;
import com.optivem.eshop.systemtest.dsl.common.BaseUseCase;
import com.optivem.eshop.systemtest.dsl.common.UseCaseContext;

public abstract class BaseShopUseCase<TResponse, TVerification> extends BaseUseCase<ShopDriver, TResponse, TVerification> {
    protected BaseShopUseCase(ShopDriver driver, UseCaseContext context) {
        super(driver, context);
    }
}


