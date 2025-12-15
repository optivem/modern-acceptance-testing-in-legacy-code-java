package com.optivem.testing.dsl;

import com.optivem.lang.Result;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("UnusedReturnValue")
public class UseCaseFailureVerification<TFailureResponse, TContext> {
    private final Result<?, TFailureResponse> result;
    private final TContext context;

    public UseCaseFailureVerification(Result<?, TFailureResponse> result, TContext context) {
        this.result = result;
        this.context = context;
    }

    public TFailureResponse getError() {
        return result.getError();
    }

    public TContext getContext() {
        return context;
    }
}

