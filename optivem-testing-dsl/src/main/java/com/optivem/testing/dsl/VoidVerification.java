package com.optivem.testing.dsl;

@SuppressWarnings("UnusedReturnValue")
public class VoidVerification<TContext> extends ResponseVerification<Void, TContext> {

    public VoidVerification(Void response, TContext context) {
        super(response, context);
    }
}

