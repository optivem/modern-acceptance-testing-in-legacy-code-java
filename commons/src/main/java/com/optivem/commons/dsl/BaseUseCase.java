package com.optivem.commons.dsl;

public abstract class BaseUseCase<TDriver, TSuccessResponse, TFailureResponse, TSuccessVerification, TFailureVerification> implements UseCase<UseCaseResult<TSuccessResponse, TFailureResponse, TSuccessVerification, TFailureVerification>> {
    protected final TDriver driver;
    protected final UseCaseContext context;

    protected BaseUseCase(TDriver driver, UseCaseContext context) {
        this.driver = driver;
        this.context = context;
    }
}