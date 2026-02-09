package com.optivem.eshop.systemtest.core.shop.dsl.common;

import com.optivem.eshop.systemtest.core.shop.commons.dtos.errors.SystemError;
import com.optivem.eshop.systemtest.core.shop.dsl.common.verifications.SystemErrorFailureVerification;
import com.optivem.commons.util.Result;
import com.optivem.commons.dsl.ResponseVerification;
import com.optivem.commons.dsl.UseCaseContext;
import com.optivem.commons.dsl.UseCaseResult;

import java.util.function.BiFunction;

public class ShopUseCaseResult<TSuccessResponse, TSuccessVerification extends ResponseVerification<TSuccessResponse>>
        extends UseCaseResult<TSuccessResponse, SystemError, TSuccessVerification, SystemErrorFailureVerification>
        implements FailureResult {

    public ShopUseCaseResult(
            Result<TSuccessResponse, SystemError> result,
            UseCaseContext context,
            BiFunction<TSuccessResponse, UseCaseContext, TSuccessVerification> verificationFactory) {
        super(result, context, verificationFactory, SystemErrorFailureVerification::new);
    }
}
