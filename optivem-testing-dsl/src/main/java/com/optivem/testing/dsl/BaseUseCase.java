package com.optivem.testing.dsl;

public abstract class BaseUseCase<TDriver, TResponse, TVerification> implements UseCase<UseCaseResult<TResponse, TVerification>> {
    protected final TDriver driver;
    protected final UseCaseContext context;

    protected BaseUseCase(TDriver driver, UseCaseContext context) {
        this.driver = driver;
        this.context = context;
    }

    public abstract UseCaseResult<TResponse, TVerification> execute();
}

