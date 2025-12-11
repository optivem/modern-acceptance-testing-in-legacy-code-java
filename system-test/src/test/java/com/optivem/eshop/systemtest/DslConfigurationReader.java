package com.optivem.eshop.systemtest;

import com.optivem.eshop.systemtest.dsl.DslConfiguration;
import org.yaml.snakeyaml.Yaml;

import java.util.Map;

public class DslConfigurationReader {
    
    private static final Map<String, Object> config;
    
    static {
        var yaml = new Yaml();
        var inputStream = DslConfigurationReader.class
                .getClassLoader()
                .getResourceAsStream("application.yml");
        config = yaml.load(inputStream);
    }

    public static DslConfiguration readConfiguration() {
        var shopUiBaseUrl = getNestedStringValue("test", "eshop", "ui", "baseUrl");
        var shopApiBaseUrl = getNestedStringValue("test", "eshop", "api", "baseUrl");
        var erpBaseUrl = getNestedStringValue("test", "erp", "api", "baseUrl");
        var taxBaseUrl = getNestedStringValue("test", "tax", "api", "baseUrl");

        return new DslConfiguration(shopUiBaseUrl, shopApiBaseUrl, erpBaseUrl, taxBaseUrl);
    }
    
    @SuppressWarnings("unchecked")
    private static <T> T getNestedValue(String... keys) {
        var current = config;
        for (int i = 0; i < keys.length - 1; i++) {
            current = (Map<String, Object>) current.get(keys[i]);
        }
        return (T) current.get(keys[keys.length - 1]);
    }

    private static String getNestedStringValue(String... keys) {
        return getNestedValue(keys);
    }
}
