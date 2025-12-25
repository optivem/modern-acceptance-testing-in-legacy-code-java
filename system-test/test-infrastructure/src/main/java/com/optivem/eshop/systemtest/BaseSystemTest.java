package com.optivem.eshop.systemtest;

import com.optivem.eshop.systemtest.core.SystemDsl;
import com.optivem.eshop.systemtest.core.gherkin.ScenarioDsl;
import com.optivem.lang.Closer;
import com.optivem.testing.channels.ChannelExtension;
import com.optivem.testing.dsl.ExternalSystemMode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(ChannelExtension.class)
public class BaseSystemTest {
    protected SystemDsl app;
    protected ScenarioDsl scenario;

    @BeforeEach
    void setUp() {
        app = createSystemDsl();
        scenario = new ScenarioDsl(app);
    }

    @AfterEach
    void tearDown() {
        Closer.close(app);
    }

    protected Environment getFixedEnvironment() {
        return null;
    }

    protected ExternalSystemMode getFixedExternalSystemMode() {
        return null;
    }

    private SystemDsl createSystemDsl() {
        var fixedEnvironment = getFixedEnvironment();
        var fixedExternalSystemMode = getFixedExternalSystemMode();

        var environment = PropertyLoader.getEnvironment(fixedEnvironment);
        var externalSystemMode = PropertyLoader.getExternalSystemMode(fixedExternalSystemMode);
        var configuration = SystemConfigurationLoader.load(environment, externalSystemMode);
        return new SystemDsl(configuration);
    }
}