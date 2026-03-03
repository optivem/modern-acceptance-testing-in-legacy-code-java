package com.optivem.eshop.systemtest.dsl.core.app.shop.usecases.base;

import com.optivem.eshop.systemtest.driver.port.shop.ShopDriver;
import com.optivem.eshop.systemtest.dsl.core.app.shared.BaseUseCase;
import com.optivem.eshop.systemtest.dsl.core.app.shared.UseCaseContext;

public abstract class BaseShopCommand<TResponse, TVerification> extends BaseUseCase<ShopDriver, TResponse, TVerification> {
    protected BaseShopCommand(ShopDriver driver, UseCaseContext context) {
        super(driver, context);
    }
}

