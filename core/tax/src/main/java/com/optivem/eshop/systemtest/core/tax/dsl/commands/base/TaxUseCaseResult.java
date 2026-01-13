package com.optivem.eshop.systemtest.core.tax.dsl.commands.base;

import com.optivem.eshop.systemtest.core.tax.driver.dtos.error.TaxErrorResponse;
import com.optivem.lang.Result;
import com.optivem.test.dsl.ResponseVerification;
import com.optivem.test.dsl.UseCaseContext;
import com.optivem.test.dsl.UseCaseResult;

import java.util.function.BiFunction;

public class TaxUseCaseResult<TSuccessResponse, TSuccessVerification extends ResponseVerification<TSuccessResponse, UseCaseContext>>
        extends UseCaseResult<TSuccessResponse, TaxErrorResponse, UseCaseContext, TSuccessVerification, TaxErrorVerification> {

    public TaxUseCaseResult(
            Result<TSuccessResponse, TaxErrorResponse> result,
            UseCaseContext context,
            BiFunction<TSuccessResponse, UseCaseContext, TSuccessVerification> verificationFactory) {
        super(result, context, verificationFactory, TaxErrorVerification::new);
    }
}
