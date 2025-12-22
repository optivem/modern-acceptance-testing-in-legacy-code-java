package com.optivem.eshop.systemtest;

import com.optivem.eshop.systemtest.core.ExternalSystemMode;
import com.optivem.eshop.systemtest.core.SystemDsl;

public class SystemDslFactory {
    public static SystemDsl create(EnvironmentMode environmentMode, ExternalSystemMode externalSystemMode) {
        var configuration = SystemConfigurationLoader.load(environmentMode, externalSystemMode);
        return new SystemDsl(configuration);
    }
}
