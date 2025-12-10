package com.optivem.testing.dsl;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Context {

    private final Map<String, String> paramMap;
    private final Map<String, String> resultMap;

    public Context() {
        this.paramMap = new HashMap<>();
        this.resultMap = new HashMap<>();
    }

    public String getParamValue(String alias) {
        if(alias == null || alias.isBlank()) {
            return alias;
        }

        if(paramMap.containsKey(alias)) {
            return paramMap.get(alias);
        }

        var value = generateParamValue(alias);
        paramMap.put(alias, value);

        return value;
    }

    public void setResultEntry(String alias, String value) {
        if(resultMap.containsKey(alias)) {
            throw new IllegalStateException("Alias already exists: " + alias);
        }

        resultMap.put(alias, value);
    }

    public String getResultValue(String alias) {
        var value = resultMap.get(alias);
        if(value == null) {
            return alias; // Return literal value if not found as alias
        }

        return value;
    }

    public String expandAliases(String message) {
        var expandedMessage = expandAlias(message, paramMap);
        expandedMessage = expandAlias(expandedMessage, resultMap);
        return expandedMessage;
    }

    private static String expandAlias(String message, Map<String, String> map) {
        var expandedMessage = message;
        for (var entry : map.entrySet()) {
            var alias = entry.getKey();
            var actualValue = entry.getValue();
            expandedMessage = expandedMessage.replace(alias, actualValue);
        }
        return expandedMessage;
    }

    private static String generateParamValue(String alias) {
        var suffix = UUID.randomUUID().toString().substring(0, 8);
        return alias + "-" + suffix;
    }


}

