package com.optivem.eshop.systemtest.core.gherkin.then;

import com.optivem.commons.dsl.ResponseVerification;
import com.optivem.commons.dsl.VoidVerification;
import com.optivem.eshop.systemtest.core.SystemDsl;
import com.optivem.eshop.systemtest.core.gherkin.ExecutionResultContext;
import com.optivem.eshop.systemtest.dsl.api.ThenFailurePort;
import com.optivem.eshop.systemtest.core.shop.dsl.usecases.base.ShopUseCaseResult;
import com.optivem.eshop.systemtest.core.shop.dsl.usecases.base.SystemErrorFailureVerification;

public class ThenFailure<TSuccessResponse, TSuccessVerification extends ResponseVerification<TSuccessResponse>>
        extends BaseThenStep<Void, VoidVerification> implements ThenFailurePort {
    private final SystemErrorFailureVerification failureVerification;

    public ThenFailure(SystemDsl app, ExecutionResultContext executionResult,
            ShopUseCaseResult<TSuccessResponse, TSuccessVerification> result) {
        super(app, executionResult, null);
        if (result == null) {
            throw new IllegalStateException("Cannot verify failure: no operation was executed");
        }
        this.failureVerification = result.shouldFail();
    }

    public ThenFailure<TSuccessResponse, TSuccessVerification> errorMessage(String expectedMessage) {
        failureVerification.errorMessage(expectedMessage);
        return this;
    }

    public ThenFailure<TSuccessResponse, TSuccessVerification> fieldErrorMessage(
            String expectedField, String expectedMessage) {
        failureVerification.fieldErrorMessage(expectedField, expectedMessage);
        return this;
    }

    @Override
    public ThenFailure<TSuccessResponse, TSuccessVerification> and() {
        return this;
    }
}
