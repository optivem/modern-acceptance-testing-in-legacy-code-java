package com.optivem.eshop.systemtest.core.shop.dsl.commands.base;

import com.optivem.eshop.systemtest.core.shop.commons.dtos.errors.SystemError;
import com.optivem.lang.Result;
import com.optivem.test.dsl.ResponseVerification;
import com.optivem.test.dsl.UseCaseContext;
import com.optivem.test.dsl.UseCaseResult;

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
