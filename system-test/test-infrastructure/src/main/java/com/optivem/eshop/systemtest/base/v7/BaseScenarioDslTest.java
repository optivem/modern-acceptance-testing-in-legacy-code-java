package com.optivem.eshop.systemtest.base.v7;

import com.optivem.eshop.systemtest.configuration.BaseConfigurableTest;
import com.optivem.eshop.systemtest.dsl.core.system.SystemDsl;
import com.optivem.eshop.systemtest.dsl.core.gherkin.ScenarioDslImpl;
import com.optivem.eshop.systemtest.dsl.api.ScenarioDslPort;
import com.optivem.commons.util.Closer;
import com.optivem.commons.playwright.BrowserLifecycleExtension;
import com.optivem.testing.extensions.ChannelExtension;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith({ChannelExtension.class, BrowserLifecycleExtension.class})
public class BaseScenarioDslTest extends BaseConfigurableTest {
    private SystemDsl app;
    protected ScenarioDslPort scenario;

    @BeforeEach
    void setUp() {
        var configuration = loadConfiguration();
        app = createSystemDsl(configuration);
        scenario = new ScenarioDslImpl(app);
    }

    @AfterEach
    void tearDown() {
        Closer.close(app);
    }
}
