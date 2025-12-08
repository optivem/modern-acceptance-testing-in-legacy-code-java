package com.optivem.eshop.systemtest.core.dsl.commons.context;

public class DslContext {

    private final ParamContext paramContext;
    private final ResultContext resultContext;

    public DslContext() {
        this.paramContext = new ParamContext();
        this.resultContext = new ResultContext();
    }

    public ParamContext params() {
        return paramContext;
    }

    public ResultContext results() {
        return resultContext;
    }
}