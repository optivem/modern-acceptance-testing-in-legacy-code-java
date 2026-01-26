package com.optivem.commons.dsl;

public class ResponseVerification<TResponse> {
    protected final TResponse response;
    protected final UseCaseContext context;

    public ResponseVerification(TResponse response, UseCaseContext context) {
        this.response = response;
        this.context = context;
    }

    public TResponse getResponse() {
        return response;
    }

    public UseCaseContext getContext() {
        return context;
    }
}
