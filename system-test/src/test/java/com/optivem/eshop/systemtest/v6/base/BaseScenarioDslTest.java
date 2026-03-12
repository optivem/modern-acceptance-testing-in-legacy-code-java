package com.optivem.eshop.systemtest.v6.base;

import com.optivem.eshop.systemtest.configuration.BaseConfigurableTest;
import com.optivem.eshop.dsl.core.app.AppDsl;
import com.optivem.eshop.dsl.core.ScenarioDslImpl;
import com.optivem.eshop.dsl.port.ScenarioDsl;
import com.optivem.eshop.dsl.common.Closer;
import com.optivem.testing.extensions.ChannelExtension;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(ChannelExtension.class)
public class BaseScenarioDslTest extends BaseConfigurableTest {
    private AppDsl app;
    protected ScenarioDsl scenario;

    @BeforeEach
    void setUp() {
        var configuration = loadConfiguration();
        this.app = createAppDsl(configuration);
        scenario = new ScenarioDslImpl(app);
    }

    @AfterEach
    void tearDown() {
        Closer.close(app);
    }
}

