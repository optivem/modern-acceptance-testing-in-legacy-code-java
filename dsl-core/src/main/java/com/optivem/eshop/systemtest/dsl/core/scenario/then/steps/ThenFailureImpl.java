package com.optivem.eshop.systemtest.dsl.core.scenario.then.steps;

import com.optivem.eshop.systemtest.dsl.core.system.shared.ResponseVerification;
import com.optivem.eshop.systemtest.dsl.core.system.shared.VoidVerification;
import com.optivem.eshop.systemtest.dsl.core.system.SystemDsl;
import com.optivem.eshop.systemtest.dsl.core.scenario.ExecutionResultContext;
import com.optivem.eshop.systemtest.dsl.api.then.steps.ThenFailure;
import com.optivem.eshop.systemtest.dsl.core.system.shop.dsl.usecases.base.ShopUseCaseResult;
import com.optivem.eshop.systemtest.dsl.core.system.shop.dsl.usecases.base.SystemErrorFailureVerification;

public class ThenFailureImpl<TSuccessResponse, TSuccessVerification extends ResponseVerification<TSuccessResponse>>
        extends BaseThenStep<Void, VoidVerification> implements ThenFailure {
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

