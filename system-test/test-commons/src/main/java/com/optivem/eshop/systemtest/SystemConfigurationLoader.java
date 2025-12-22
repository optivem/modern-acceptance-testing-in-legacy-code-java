package com.optivem.eshop.systemtest;

import com.optivem.eshop.systemtest.core.ExternalSystemMode;
import com.optivem.eshop.systemtest.core.SystemConfiguration;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Map;

public class SystemConfigurationLoader {
    
    public static SystemConfiguration load(EnvironmentMode environmentMode, ExternalSystemMode externalSystemMode) {
        String configFile = getConfigFileName(environmentMode, externalSystemMode);
        Map<String, Object> config = loadYamlFile(configFile);

        var shopUiBaseUrl = getNestedStringValue(config, "test", "eshop", "ui", "baseUrl");
        var shopApiBaseUrl = getNestedStringValue(config, "test", "eshop", "api", "baseUrl");
        var erpBaseUrl = getNestedStringValue(config, "test", "erp", "api", "baseUrl");
        var taxBaseUrl = getNestedStringValue(config, "test", "tax", "api", "baseUrl");

        return new SystemConfiguration(shopUiBaseUrl, shopApiBaseUrl, erpBaseUrl, taxBaseUrl, externalSystemMode);
    }

    private static String getConfigFileName(EnvironmentMode environmentMode, ExternalSystemMode externalSystemMode) {
        // Only ACCEPTANCE environment can use STUB mode
        if (externalSystemMode == ExternalSystemMode.STUB && environmentMode != EnvironmentMode.ACCEPTANCE) {
            throw new IllegalArgumentException(
                String.format("STUB mode is only allowed for ACCEPTANCE environment. Cannot use STUB for %s environment.",
                    environmentMode)
            );
        }

        String env = environmentMode.name().toLowerCase();
        String mode = externalSystemMode.name().toLowerCase();
        return String.format("test-config-%s-%s.yml", env, mode);
    }

    private static Map<String, Object> loadYamlFile(String fileName) {
        var yaml = new Yaml();
        InputStream inputStream = SystemConfigurationLoader.class
                .getClassLoader()
                .getResourceAsStream(fileName);

        if (inputStream == null) {
            throw new IllegalStateException("Configuration file not found: " + fileName);
        }

        return yaml.load(inputStream);
    }
    
    @SuppressWarnings("unchecked")
    private static <T> T getNestedValue(Map<String, Object> config, String... keys) {
        var current = config;
        for (int i = 0; i < keys.length - 1; i++) {
            current = (Map<String, Object>) current.get(keys[i]);
        }
        return (T) current.get(keys[keys.length - 1]);
    }

    private static String getNestedStringValue(Map<String, Object> config, String... keys) {
        return getNestedValue(config, keys);
    }
}
