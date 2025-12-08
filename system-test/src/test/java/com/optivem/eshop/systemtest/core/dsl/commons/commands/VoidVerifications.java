package com.optivem.eshop.systemtest.core.dsl.commons.commands;

import com.optivem.eshop.systemtest.core.dsl.commons.context.DslContext;

@SuppressWarnings("UnusedReturnValue")
public class VoidVerifications extends BaseSuccessResult<Void> {

    public VoidVerifications(Void response, DslContext context) {
        super(response, context);
    }
}

