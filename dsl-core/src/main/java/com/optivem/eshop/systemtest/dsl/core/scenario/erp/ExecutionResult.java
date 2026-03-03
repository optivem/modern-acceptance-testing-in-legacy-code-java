package com.optivem.eshop.systemtest.dsl.core.scenario.erp;

import com.optivem.eshop.systemtest.dsl.core.app.shared.UseCaseResult;
import com.optivem.eshop.systemtest.dsl.core.app.shared.ResponseVerification;
import lombok.Getter;

@Getter
public class ExecutionResult<TSuccessResponse, TSuccessVerification extends ResponseVerification<TSuccessResponse>> {
    private final UseCaseResult<TSuccessResponse, TSuccessVerification> result;
    private final ExecutionResultContext context;

    ExecutionResult(UseCaseResult<TSuccessResponse, TSuccessVerification> result, String productSku) {
        if (result == null) {
            throw new IllegalArgumentException("Result cannot be null");
        }
        this.result = result;
        this.context = new ExecutionResultContext(productSku);
    }
}
