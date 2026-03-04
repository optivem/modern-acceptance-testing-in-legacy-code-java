package com.optivem.eshop.systemtest.dsl.core.scenario.shop.then;

import com.optivem.eshop.systemtest.dsl.core.app.shared.ResponseVerification;
import com.optivem.eshop.systemtest.dsl.core.app.AppDsl;
import com.optivem.eshop.systemtest.dsl.core.scenario.shop.ExecutionResult;
import com.optivem.eshop.systemtest.dsl.core.scenario.shop.then.steps.ThenFailureImpl;
import com.optivem.eshop.systemtest.dsl.core.scenario.shop.then.steps.ThenSuccessImpl;

public class ThenImpl<TSuccessResponse, TSuccessVerification extends ResponseVerification<TSuccessResponse>> extends BaseThenImpl {
    private final ExecutionResult<TSuccessResponse, TSuccessVerification> executionResult;

    public ThenImpl(AppDsl app, ExecutionResult<TSuccessResponse, TSuccessVerification> executionResult) {
        super(app);
        this.executionResult = executionResult;
    }

    @Override
    public ThenSuccessImpl<TSuccessResponse, TSuccessVerification> shouldSucceed() {
        var successVerification = executionResult.getResult().shouldSucceed();
        return new ThenSuccessImpl<>(app, executionResult.getContext(), successVerification);
    }

    @Override
    public ThenFailureImpl<TSuccessResponse, TSuccessVerification> shouldFail() {
        return new ThenFailureImpl<>(app, executionResult.getContext(), executionResult.getResult());
    }
}
