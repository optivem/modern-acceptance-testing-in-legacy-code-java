package com.optivem.eshop.systemtest.core.tax.dsl.commands.base;

import com.optivem.eshop.systemtest.core.tax.commons.TaxError;
import com.optivem.lang.Result;
import com.optivem.testing.dsl.UseCaseContext;
import com.optivem.testing.dsl.UseCaseResult;
import com.optivem.testing.dsl.ResponseVerification;

import java.util.function.BiFunction;

public class TaxUseCaseResult<TSuccessResponse, TSuccessVerification extends ResponseVerification<TSuccessResponse, UseCaseContext>>
        extends UseCaseResult<TSuccessResponse, TaxError, UseCaseContext, TSuccessVerification, TaxErrorVerification> {

    public TaxUseCaseResult(
            Result<TSuccessResponse, TaxError> result,
            UseCaseContext context,
            BiFunction<TSuccessResponse, UseCaseContext, TSuccessVerification> verificationFactory) {
        super(result, context, verificationFactory, TaxErrorVerification::new);
    }
}
