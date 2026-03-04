package com.optivem.eshop.systemtest.base.v6;

import com.optivem.eshop.systemtest.configuration.BaseConfigurableTest;
import com.optivem.eshop.systemtest.dsl.core.app.AppDsl;
import com.optivem.eshop.systemtest.dsl.core.BackgroundDslImpl;
import com.optivem.eshop.systemtest.dsl.core.ScenarioDslImpl;
import com.optivem.eshop.systemtest.dsl.port.BackgroundDsl;
import com.optivem.eshop.systemtest.dsl.port.ScenarioDsl;
import com.optivem.common.Closer;
import com.optivem.testing.extensions.ChannelExtension;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(ChannelExtension.class)
public class BaseScenarioDslTest extends BaseConfigurableTest {
    private AppDsl app;
    protected BackgroundDsl background;
    protected ScenarioDsl scenario;

    @BeforeEach
    void setUp() {
        var configuration = loadConfiguration();
        this.app = createAppDsl(configuration);
        background = new BackgroundDslImpl(app);
        scenario = new ScenarioDslImpl(app);
    }

    @AfterEach
    void tearDown() {
        Closer.close(app);
    }
}

