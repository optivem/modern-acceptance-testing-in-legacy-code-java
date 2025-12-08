package com.optivem.eshop.systemtest.core.dsl.commons.context;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ParamContext {
    private final Map<String, String> map;

    public ParamContext() {
        this.map = new HashMap<>();
    }

    public String getValue(String alias) {
        if(alias == null || alias.isBlank()) {
            return alias;
        }

        if(map.containsKey(alias)) {
            return map.get(alias);
        }

        var value = generateRandomValue(alias);
        map.put(alias, value);

        return value;
    }

    public Map<String, String> getEntries() {
        return new HashMap<>(map);
    }

    private static String generateRandomValue(String alias) {
        var suffix = UUID.randomUUID().toString().substring(0, 8);
        return alias + "-" + suffix;
    }
}