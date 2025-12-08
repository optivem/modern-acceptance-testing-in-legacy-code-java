package com.optivem.eshop.systemtest.core.dsl.commons.commands;

import com.optivem.eshop.systemtest.core.dsl.commons.context.DslContext;

public abstract class BaseSuccessResult<TResponse> {
    protected final TResponse response;
    protected final DslContext context;

    protected BaseSuccessResult(TResponse response, DslContext context) {
        this.response = response;
        this.context = context;
    }
}

