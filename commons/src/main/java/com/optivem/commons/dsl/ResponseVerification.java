package com.optivem.commons.dsl;

public class ResponseVerification<TResponse, TContext> {
    protected final TResponse response;
    protected final TContext context;

    public ResponseVerification(TResponse response, TContext context) {
        this.response = response;
        this.context = context;
    }

    public TResponse getResponse() {
        return response;
    }

    public TContext getContext() {
        return context;
    }
}
