package com.optivem.eshop.systemtest.base.v7;

import com.optivem.eshop.systemtest.configuration.BaseConfigurableTest;
import com.optivem.eshop.systemtest.core.SystemDsl;
import com.optivem.eshop.systemtest.core.gherkin.ScenarioDsl;
import com.optivem.lang.Closer;
import com.optivem.playwright.BrowserLifecycleExtension;
import com.optivem.testing.channels.ChannelExtension;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith({ChannelExtension.class, BrowserLifecycleExtension.class})
public class BaseScenarioDslTest extends BaseConfigurableTest {
    protected SystemDsl app;
    protected ScenarioDsl scenario;

    @BeforeEach
    void setUp() {
        var configuration = loadConfiguration();
        app = new SystemDsl(configuration);
        scenario = new ScenarioDsl(app);
    }

    @AfterEach
    void tearDown() {
        Closer.close(app);
    }
}
