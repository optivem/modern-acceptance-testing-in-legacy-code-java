package com.optivem.eshop.systemtest;

import com.optivem.eshop.systemtest.core.SystemDsl;
import com.optivem.testing.dsl.ExternalSystemMode;

public class SystemDslFactory {

    public static SystemDsl create(Environment fixedEnvironment, ExternalSystemMode fixedExternalSystemMode) {
        var environmentMode = getEnvironment(fixedEnvironment);
        var externalSystemMode = getExternalSystemMode(fixedExternalSystemMode);
        var configuration = SystemConfigurationLoader.load(environmentMode, externalSystemMode);
        return new SystemDsl(configuration);
    }

    public static SystemDsl create(ExternalSystemMode externalSystemMode) {
        return create(null, externalSystemMode);
    }

    public static SystemDsl create() {
        return create(null, null);
    }

    private static Environment getEnvironment(Environment fixedEnvironment) {
        if (fixedEnvironment != null) {
            return fixedEnvironment;
        }

        var environmentMode = getRequiredSystemProperty("environment", "local|acceptance|qa|production");
        return Environment.valueOf(environmentMode.toUpperCase());
    }

    private static ExternalSystemMode getExternalSystemMode(ExternalSystemMode fixedExternalSystemMode) {
        if (fixedExternalSystemMode != null) {
            return fixedExternalSystemMode;
        }

        var externalSystemMode = getRequiredSystemProperty("externalSystemMode", "stub|real");
        return ExternalSystemMode.valueOf(externalSystemMode.toUpperCase());
    }

    private static String getRequiredSystemProperty(String propertyName, String allowedValues) {
        var value = System.getProperty(propertyName);
        if (value == null) {
            throw new IllegalStateException(
                    String.format("System property '%s' is not defined. Please specify -D%s=<%s>",
                            propertyName, propertyName, allowedValues)
            );
        }
        return value;
    }
}
