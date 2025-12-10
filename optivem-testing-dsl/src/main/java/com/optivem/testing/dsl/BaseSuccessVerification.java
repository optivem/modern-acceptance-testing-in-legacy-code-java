package com.optivem.testing.dsl;

public abstract class BaseSuccessVerification<TResponse> {
    protected final TResponse response;
    protected final Context context;

    protected BaseSuccessVerification(TResponse response, Context context) {
        this.response = response;
        this.context = context;
    }
}

