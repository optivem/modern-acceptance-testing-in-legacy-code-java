package com.optivem.eshop.systemtest.dsl.core.system.shop.dsl.usecases.base;

import com.optivem.eshop.systemtest.driver.api.shop.dtos.errors.SystemError;
import com.optivem.commons.util.Result;
import com.optivem.eshop.systemtest.dsl.core.system.shared.ResponseVerification;
import com.optivem.eshop.systemtest.dsl.core.system.shared.UseCaseContext;
import com.optivem.eshop.systemtest.dsl.core.system.shared.UseCaseResult;

import java.util.function.BiFunction;

public class ShopUseCaseResult<TSuccessResponse, TSuccessVerification extends ResponseVerification<TSuccessResponse>>
        extends UseCaseResult<TSuccessResponse, SystemError, TSuccessVerification, SystemErrorFailureVerification> {

    public ShopUseCaseResult(
            Result<TSuccessResponse, SystemError> result,
            UseCaseContext context,
            BiFunction<TSuccessResponse, UseCaseContext, TSuccessVerification> verificationFactory) {
        super(result, context, verificationFactory, SystemErrorFailureVerification::new);
    }
}
