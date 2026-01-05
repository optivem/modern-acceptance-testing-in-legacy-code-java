package com.optivem.eshop.systemtest.configuration;

import com.optivem.eshop.systemtest.core.SystemConfiguration;
import com.optivem.playwright.BrowserLifecycleExtension;
import com.optivem.testing.channels.ChannelExtension;
import com.optivem.testing.dsl.ExternalSystemMode;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(BrowserLifecycleExtension.class)
public abstract class BaseConfigurableTest {
    protected Environment getFixedEnvironment() {
        return null;
    }

    protected ExternalSystemMode getFixedExternalSystemMode() {
        return null;
    }

    protected SystemConfiguration loadConfiguration() {
        var fixedEnvironment = getFixedEnvironment();
        var fixedExternalSystemMode = getFixedExternalSystemMode();

        var environment = PropertyLoader.getEnvironment(fixedEnvironment);
        var externalSystemMode = PropertyLoader.getExternalSystemMode(fixedExternalSystemMode);
        return SystemConfigurationLoader.load(environment, externalSystemMode);
    }
}
