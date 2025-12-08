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

    public void setResultEntry(String alias, String value) {
        resultContext.setEntry(alias, value);
    }

    public String getResultValue(String alias) {
        return resultContext.getValue(alias);
    }

    public String expandAliases(String message) {
        var expandedMessage = message;
        var aliases = paramContext.getEntries();
        for (var entry : aliases.entrySet()) {
            var alias = entry.getKey();
            var actualValue = entry.getValue();
            expandedMessage = expandedMessage.replace(alias, actualValue);
        }
        return expandedMessage;
    }
}

