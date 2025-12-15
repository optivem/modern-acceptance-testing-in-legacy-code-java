package com.optivem.testing.dsl;

public abstract class BaseUseCaseSuccessVerification<TResponse> {
    protected final TResponse response;
    protected final UseCaseContext context;

    protected BaseUseCaseSuccessVerification(TResponse response, UseCaseContext context) {
        this.response = response;
        this.context = context;
    }
}

