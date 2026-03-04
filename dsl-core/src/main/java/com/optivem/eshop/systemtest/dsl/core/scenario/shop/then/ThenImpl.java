package com.optivem.eshop.systemtest.dsl.core.scenario.shop.then;

import com.optivem.eshop.systemtest.dsl.core.app.shared.ResponseVerification;
import com.optivem.eshop.systemtest.dsl.core.app.AppDsl;
import com.optivem.eshop.systemtest.dsl.core.scenario.shop.ExecutionResult;
import com.optivem.eshop.systemtest.dsl.core.scenario.shop.then.steps.ThenClockImpl;
import com.optivem.eshop.systemtest.dsl.core.scenario.shop.then.steps.ThenFailureImpl;
import com.optivem.eshop.systemtest.dsl.core.scenario.shop.then.steps.ThenSuccessImpl;
import com.optivem.eshop.systemtest.dsl.port.shop.then.Then;
import com.optivem.eshop.systemtest.dsl.port.shop.then.steps.ThenClock;

public class ThenImpl<TSuccessResponse, TSuccessVerification extends ResponseVerification<TSuccessResponse>> implements Then {
    private final AppDsl app;
    private final ExecutionResult<TSuccessResponse, TSuccessVerification> executionResult;

    public ThenImpl(AppDsl app, ExecutionResult<TSuccessResponse, TSuccessVerification> executionResult) {
        this.app = app;
        this.executionResult = executionResult;
    }

    public ThenClock clock() {
        var verification = app.clock().getTime().execute().shouldSucceed();
        return new ThenClockImpl(verification);
    }

    public ThenSuccessImpl<TSuccessResponse, TSuccessVerification> shouldSucceed() {
        if (executionResult == null) {
            throw new IllegalStateException("Cannot verify success: no operation was executed");
        }
        var successVerification = executionResult.getResult().shouldSucceed();
        return new ThenSuccessImpl<>(app, executionResult.getContext(), successVerification);
    }

    public ThenFailureImpl<TSuccessResponse, TSuccessVerification> shouldFail() {
        return new ThenFailureImpl<>(app, executionResult.getContext(), executionResult.getResult());
    }
}

