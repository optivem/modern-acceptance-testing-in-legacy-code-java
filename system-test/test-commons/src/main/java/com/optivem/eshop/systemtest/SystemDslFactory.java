package com.optivem.eshop.systemtest;

import com.optivem.eshop.systemtest.core.SystemDsl;

public class SystemDslFactory {
    public static SystemDsl create() {
        var configuration = SystemConfigurationLoader.load();
        return new SystemDsl(configuration);
    }
}
