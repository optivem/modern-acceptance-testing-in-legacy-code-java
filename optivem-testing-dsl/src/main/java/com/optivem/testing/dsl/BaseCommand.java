package com.optivem.testing.dsl;

public abstract class BaseCommand<TDriver, TResponse, TVerification> implements Command<CommandResult<TResponse, TVerification>> {
    protected final TDriver driver;
    protected final Context context;

    protected BaseCommand(TDriver driver, Context context) {
        this.driver = driver;
        this.context = context;
    }

    public abstract CommandResult<TResponse, TVerification> execute();
}

