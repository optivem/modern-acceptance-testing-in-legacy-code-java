package com.optivem.eshop.systemtest.core.shop.dsl.usecases.base;

import com.optivem.eshop.systemtest.driver.api.shop.driver.ShopDriver;
import com.optivem.eshop.systemtest.driver.api.shop.commons.dtos.errors.SystemError;
import com.optivem.commons.dsl.BaseUseCase;
import com.optivem.commons.dsl.UseCaseContext;

public abstract class BaseShopCommand<TResponse, TVerification> extends BaseUseCase<ShopDriver, TResponse, SystemError, TVerification, SystemErrorFailureVerification> {
    protected BaseShopCommand(ShopDriver driver, UseCaseContext context) {
        super(driver, context);
    }
}
