package com.optivem.eshop.systemtest.core.shop.dsl.commands.base;

import com.optivem.eshop.systemtest.core.shop.commons.dtos.error.SystemError;
import com.optivem.lang.Result;
import com.optivem.testing.dsl.ResponseVerification;
import com.optivem.testing.dsl.UseCaseContext;
import com.optivem.testing.dsl.UseCaseResult;

import java.util.function.BiFunction;

public class ShopUseCaseResult<TSuccessResponse, TSuccessVerification extends ResponseVerification<TSuccessResponse, UseCaseContext>>
        extends UseCaseResult<TSuccessResponse, SystemError, UseCaseContext, TSuccessVerification, ErrorFailureVerification> {

    public ShopUseCaseResult(
            Result<TSuccessResponse, SystemError> result,
            UseCaseContext context,
            BiFunction<TSuccessResponse, UseCaseContext, TSuccessVerification> verificationFactory) {
        super(result, context, verificationFactory, ErrorFailureVerification::new);
    }
}
