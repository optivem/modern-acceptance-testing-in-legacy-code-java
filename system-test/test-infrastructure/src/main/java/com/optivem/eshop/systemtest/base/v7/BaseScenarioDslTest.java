package com.optivem.eshop.systemtest.base.v7;

import com.optivem.eshop.systemtest.configuration.BaseConfigurableTest;
import com.optivem.eshop.systemtest.dsl.core.app.AppDsl;
import com.optivem.eshop.systemtest.dsl.core.FeatureDslImpl;
import com.optivem.eshop.systemtest.dsl.port.ScenarioDsl;
import com.optivem.eshop.systemtest.dsl.port.BackgroundDsl;
import com.optivem.common.Closer;
import com.optivem.eshop.systemtest.infrastructure.playwright.BrowserLifecycleExtension;
import com.optivem.testing.extensions.ChannelExtension;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith({ChannelExtension.class, BrowserLifecycleExtension.class})
public class BaseScenarioDslTest extends BaseConfigurableTest {
    private AppDsl app;
    protected BackgroundDsl background;
    protected ScenarioDsl scenario;

    @BeforeEach
    void setUp() {
        var configuration = loadConfiguration();
        app = createAppDsl(configuration);
        var feature = new FeatureDslImpl(app);
        background = feature.background();
        scenario = feature.scenario();
    }

    @AfterEach
    void tearDown() {
        Closer.close(app);
    }
}

