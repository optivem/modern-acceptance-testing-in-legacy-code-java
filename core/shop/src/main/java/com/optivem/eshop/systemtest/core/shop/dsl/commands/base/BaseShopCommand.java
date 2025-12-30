package com.optivem.eshop.systemtest.core.shop.dsl.commands.base;

import com.optivem.eshop.systemtest.core.shop.driver.ShopDriver;
import com.optivem.eshop.systemtest.core.shop.commons.dtos.error.SystemError;
import com.optivem.testing.dsl.BaseUseCase;
import com.optivem.testing.dsl.UseCaseContext;

public abstract class BaseShopCommand<TResponse, TVerification> extends BaseUseCase<ShopDriver, UseCaseContext, TResponse, SystemError, TVerification, ErrorFailureVerification> {
    protected BaseShopCommand(ShopDriver driver, UseCaseContext context) {
        super(driver, context);
    }
}

