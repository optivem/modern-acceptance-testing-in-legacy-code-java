package com.optivem.eshop.systemtest.dsl.core.scenario.erp;

import com.optivem.eshop.systemtest.dsl.core.app.shared.UseCaseResult;
import com.optivem.eshop.systemtest.dsl.core.app.shared.ResponseVerification;

public class ExecutionResultBuilder<TSuccessResponse, TSuccessVerification extends ResponseVerification<TSuccessResponse>> {
    private final UseCaseResult<TSuccessResponse, TSuccessVerification> result;
    private String productSku;

    public ExecutionResultBuilder(UseCaseResult<TSuccessResponse, TSuccessVerification> result) {
        this.result = result;
    }

    public ExecutionResultBuilder<TSuccessResponse, TSuccessVerification> productSku(String productSku) {
        this.productSku = productSku;
        return this;
    }

    public ExecutionResult<TSuccessResponse, TSuccessVerification> build() {
        return new ExecutionResult<>(result, productSku);
    }
}
