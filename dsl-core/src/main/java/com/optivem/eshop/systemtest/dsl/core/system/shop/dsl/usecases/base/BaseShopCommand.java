package com.optivem.eshop.systemtest.dsl.core.system.shop.dsl.usecases.base;

import com.optivem.eshop.systemtest.driver.api.shop.ShopDriver;
import com.optivem.eshop.systemtest.driver.api.shop.dtos.errors.SystemError;
import com.optivem.eshop.systemtest.dsl.core.system.shared.BaseUseCase;
import com.optivem.eshop.systemtest.dsl.core.system.shared.UseCaseContext;

public abstract class BaseShopCommand<TResponse, TVerification> extends BaseUseCase<ShopDriver, TResponse, SystemError, TVerification, SystemErrorFailureVerification> {
    protected BaseShopCommand(ShopDriver driver, UseCaseContext context) {
        super(driver, context);
    }
}
