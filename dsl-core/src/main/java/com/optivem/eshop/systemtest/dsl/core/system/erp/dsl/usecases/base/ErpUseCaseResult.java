package com.optivem.eshop.systemtest.dsl.core.system.erp.dsl.usecases.base;

import com.optivem.eshop.systemtest.driver.api.erp.dtos.error.ErpErrorResponse;
import com.optivem.commons.util.Result;
import com.optivem.commons.dsl.UseCaseContext;
import com.optivem.commons.dsl.UseCaseResult;

import java.util.function.BiFunction;

public class ErpUseCaseResult<TSuccessResponse, TSuccessVerification>
        extends UseCaseResult<TSuccessResponse, ErpErrorResponse, TSuccessVerification, ErpErrorVerification> {

    public ErpUseCaseResult(
            Result<TSuccessResponse, ErpErrorResponse> result,
            UseCaseContext context,
            BiFunction<TSuccessResponse, UseCaseContext, TSuccessVerification> verificationFactory) {
        super(result, context, verificationFactory, ErpErrorVerification::new);
    }
}
