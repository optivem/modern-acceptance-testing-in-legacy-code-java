package com.optivem.commons.dsl;

public abstract class BaseUseCase<TDriver, TContext, TSuccessResponse, TFailureResponse, TSuccessVerification, TFailureVerification> implements UseCase<UseCaseResult<TSuccessResponse, TFailureResponse, TContext, TSuccessVerification, TFailureVerification>> {
    protected final TDriver driver;
    protected final TContext context;

    protected BaseUseCase(TDriver driver, TContext context) {
        this.driver = driver;
        this.context = context;
    }

    public abstract UseCaseResult<TSuccessResponse, TFailureResponse, TContext, TSuccessVerification, TFailureVerification> execute();
}