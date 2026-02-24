package com.optivem.eshop.systemtest.core.gherkin.then.steps;

import com.optivem.commons.dsl.ResponseVerification;
import com.optivem.commons.dsl.VoidVerification;
import com.optivem.eshop.systemtest.core.system.SystemDsl;
import com.optivem.eshop.systemtest.core.gherkin.ExecutionResultContext;
import com.optivem.eshop.systemtest.dsl.api.then.steps.ThenFailurePort;
import com.optivem.eshop.systemtest.core.system.shop.dsl.usecases.base.ShopUseCaseResult;
import com.optivem.eshop.systemtest.core.system.shop.dsl.usecases.base.SystemErrorFailureVerification;

public class ThenFailureImpl<TSuccessResponse, TSuccessVerification extends ResponseVerification<TSuccessResponse>>
        extends BaseThenStep<Void, VoidVerification> implements ThenFailurePort {
    private final SystemErrorFailureVerification failureVerification;

    public ThenFailureImpl(SystemDsl app, ExecutionResultContext executionResult,
            ShopUseCaseResult<TSuccessResponse, TSuccessVerification> result) {
        super(app, executionResult, null);
        if (result == null) {
            throw new IllegalStateException("Cannot verify failure: no operation was executed");
        }
        this.failureVerification = result.shouldFail();
    }

    public ThenFailureImpl<TSuccessResponse, TSuccessVerification> errorMessage(String expectedMessage) {
        failureVerification.errorMessage(expectedMessage);
        return this;
    }

    public ThenFailureImpl<TSuccessResponse, TSuccessVerification> fieldErrorMessage(
            String expectedField, String expectedMessage) {
        failureVerification.fieldErrorMessage(expectedField, expectedMessage);
        return this;
    }

    @Override
    public ThenFailureImpl<TSuccessResponse, TSuccessVerification> and() {
        return this;
    }
}
