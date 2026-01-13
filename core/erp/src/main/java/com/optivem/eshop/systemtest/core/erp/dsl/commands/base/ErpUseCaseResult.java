package com.optivem.eshop.systemtest.core.erp.dsl.commands.base;

import com.optivem.eshop.systemtest.core.erp.driver.dtos.error.ErpErrorResponse;
import com.optivem.lang.Result;
import com.optivem.test.dsl.UseCaseContext;
import com.optivem.test.dsl.UseCaseResult;

import java.util.function.BiFunction;

public class ErpUseCaseResult<TSuccessResponse, TSuccessVerification>
        extends UseCaseResult<TSuccessResponse, ErpErrorResponse, UseCaseContext, TSuccessVerification, ErpErrorVerification> {

    public ErpUseCaseResult(
            Result<TSuccessResponse, ErpErrorResponse> result,
            UseCaseContext context,
            BiFunction<TSuccessResponse, UseCaseContext, TSuccessVerification> verificationFactory) {
        super(result, context, verificationFactory, ErpErrorVerification::new);
    }
}
