package com.optivem.eshop.systemtest.core.tax.dsl.commands.base;

import com.optivem.eshop.systemtest.core.common.dsl.ErrorFailureVerification;
import com.optivem.eshop.systemtest.core.common.error.Error;
import com.optivem.lang.Result;
import com.optivem.testing.dsl.UseCaseContext;
import com.optivem.testing.dsl.UseCaseResult;
import com.optivem.testing.dsl.ResponseVerification;

import java.util.function.BiFunction;

public class TaxUseCaseResult<TSuccessResponse, TSuccessVerification extends ResponseVerification<TSuccessResponse, UseCaseContext>>
        extends UseCaseResult<TSuccessResponse, TSuccessVerification, Error, ErrorFailureVerification, UseCaseContext> {

    public TaxUseCaseResult(
            Result<TSuccessResponse, Error> result,
            UseCaseContext context,
            BiFunction<TSuccessResponse, UseCaseContext, TSuccessVerification> verificationFactory) {
        super(result, context, verificationFactory, ErrorFailureVerification::new);
    }
}
