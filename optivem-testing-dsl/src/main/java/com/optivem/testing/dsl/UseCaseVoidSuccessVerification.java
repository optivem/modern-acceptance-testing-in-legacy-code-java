package com.optivem.testing.dsl;

@SuppressWarnings("UnusedReturnValue")
public class UseCaseVoidSuccessVerification extends BaseUseCaseSuccessVerification<Void> {

    public UseCaseVoidSuccessVerification(Void response, UseCaseContext context) {
        super(response, context);
    }
}

