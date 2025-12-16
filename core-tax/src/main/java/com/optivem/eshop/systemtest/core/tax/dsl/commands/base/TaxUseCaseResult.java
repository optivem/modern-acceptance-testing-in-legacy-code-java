package com.optivem.eshop.systemtest.core.tax.dsl.commands.base;

import com.optivem.eshop.systemtest.core.commons.dsl.ErrorFailureVerification;
import com.optivem.eshop.systemtest.core.commons.error.Error;
import com.optivem.lang.Result;
import com.optivem.testing.dsl.UseCaseContext;
import com.optivem.testing.dsl.UseCaseResult;
import com.optivem.testing.dsl.ResponseVerification;

import java.util.function.BiFunction;

public class TaxUseCaseResult<TSuccessResponse, TSuccessVerification extends ResponseVerification<TSuccessResponse, UseCaseContext>>
        extends UseCaseResult<TSuccessResponse, Error, UseCaseContext, TSuccessVerification, ErrorFailureVerification> {

    public TaxUseCaseResult(
            Result<TSuccessResponse, Error> result,
            UseCaseContext context,
            BiFunction<TSuccessResponse, UseCaseContext, TSuccessVerification> verificationFactory) {
        super(result, context, verificationFactory, ErrorFailureVerification::new);
    }
}
