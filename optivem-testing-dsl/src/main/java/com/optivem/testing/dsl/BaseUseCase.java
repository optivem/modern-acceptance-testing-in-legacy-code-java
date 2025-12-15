package com.optivem.testing.dsl;

public abstract class BaseUseCase<TDriver, TContext, TSuccessResponse, TSuccessVerification, TFailureResponse, TFailureVerification extends ResponseVerification<TFailureResponse, TContext>> implements UseCase<UseCaseResult<TSuccessResponse, TSuccessVerification, TFailureResponse, TFailureVerification, TContext>> {
    protected final TDriver driver;
    protected final TContext context;

    protected BaseUseCase(TDriver driver, TContext context) {
        this.driver = driver;
        this.context = context;
    }

    public abstract UseCaseResult<TSuccessResponse, TSuccessVerification, TFailureResponse, TFailureVerification, TContext> execute();
}