package com.optivem.eshop.systemtest;

import com.optivem.eshop.systemtest.core.SystemDsl;

public class SystemDslFactory {
    public static SystemDsl create() {
        var configuration = SystemConfigurationReader.readConfiguration();
        return new SystemDsl(configuration);
    }
}
