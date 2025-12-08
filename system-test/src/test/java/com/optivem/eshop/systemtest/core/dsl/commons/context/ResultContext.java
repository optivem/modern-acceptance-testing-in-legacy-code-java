package com.optivem.eshop.systemtest.core.dsl.commons.context;

import com.optivem.results.Result;

import java.util.HashMap;
import java.util.Map;

public class ResultContext {
    private final Map<String, String> aliasMap;
    private final Map<String, Result<?>> resultMap;

    public ResultContext() {
        this.aliasMap = new HashMap<>();
        this.resultMap = new HashMap<>();
    }

    public void setAliasValue(String alias, String value) {
        if(aliasMap.containsKey(alias)) {
            throw new IllegalStateException("Alias already exists: " + alias);
        }

        aliasMap.put(alias, value);
    }

    public String getAliasValue(String alias) {
        var value = aliasMap.get(alias);
        if(value == null) {
            throw new IllegalStateException("Alias not found: " + alias);
        }

        return value;
    }

    public <T> void registerResult(String command, Result<T> result) {
        if(resultMap.containsKey(command)) {
            throw new IllegalStateException("Result already registered for command: " + command);
        }
        resultMap.put(command, result);
    }

    public <T> void registerResult(String command, String key, Result<T> result) {
        String compositeKey = command + ":" + key;
        if(resultMap.containsKey(compositeKey)) {
            throw new IllegalStateException("Result already registered for command and key: " + compositeKey);
        }
        resultMap.put(compositeKey, result);
    }

    @SuppressWarnings("unchecked")
    public <T> Result<T> getResult(String command, String key, Class<T> type) {
        String compositeKey = command + ":" + key;
        var result = resultMap.get(compositeKey);
        if(result == null) {
            throw new IllegalStateException("Result not found for command and key: " + compositeKey);
        }
        return (Result<T>) result;
    }

    @SuppressWarnings("unchecked")
    public <T> Result<T> getResult(String command, Class<T> type) {
        var result = resultMap.get(command);
        if(result == null) {
            throw new IllegalStateException("Result not found for command: " + command);
        }
        return (Result<T>) result;
    }
}