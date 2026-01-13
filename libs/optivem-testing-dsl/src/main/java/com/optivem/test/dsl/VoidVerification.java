package com.optivem.test.dsl;

public class VoidVerification<TContext> extends ResponseVerification<Void, TContext> {

    public VoidVerification(Void response, TContext context) {
        super(response, context);
    }
}

