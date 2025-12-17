package com.optivem.testing.dsl;

public class VoidResponseVerification<TContext> extends ResponseVerification<Void, TContext> {

    public VoidResponseVerification(Void response, TContext context) {
        super(response, context);
    }
}

