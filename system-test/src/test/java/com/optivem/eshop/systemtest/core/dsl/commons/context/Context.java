package com.optivem.eshop.systemtest.core.dsl.commons.context;

import java.util.Map;

public class Context {

    private final ParamContext paramContext;
    private final ResultContext resultContext;

    public Context() {
        this.paramContext = new ParamContext();
        this.resultContext = new ResultContext();
    }

    public String getParamValue(String alias) {
        return paramContext.getValue(alias);
    }

    public Map<String, String> getParamEntries() {
        return paramContext.getEntries();
    }

    public void setResultEntry(String alias, String value) {
        resultContext.setEntry(alias, value);
    }

    public String getResultValue(String alias) {
        return resultContext.getValue(alias);
    }
}

