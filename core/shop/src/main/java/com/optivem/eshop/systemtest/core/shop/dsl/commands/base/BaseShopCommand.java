package com.optivem.eshop.systemtest.core.shop.dsl.commands.base;

import com.optivem.eshop.systemtest.core.shop.driver.ShopDriver;
import com.optivem.eshop.systemtest.core.shop.commons.dtos.errors.SystemError;
import com.optivem.test.dsl.BaseUseCase;
import com.optivem.test.dsl.UseCaseContext;

public abstract class BaseShopCommand<TResponse, TVerification> extends BaseUseCase<ShopDriver, UseCaseContext, TResponse, SystemError, TVerification, ErrorFailureVerification> {
    protected BaseShopCommand(ShopDriver driver, UseCaseContext context) {
        super(driver, context);
    }
}

