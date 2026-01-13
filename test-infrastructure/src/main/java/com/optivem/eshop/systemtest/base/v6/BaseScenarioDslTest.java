package com.optivem.eshop.systemtest.base.v6;

import com.optivem.eshop.systemtest.configuration.BaseConfigurableTest;
import com.optivem.eshop.systemtest.core.SystemDsl;
import com.optivem.eshop.systemtest.core.gherkin.ScenarioDsl;
import com.optivem.lang.Closer;
import com.optivem.test.extensions.ChannelExtension;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(ChannelExtension.class)
public class BaseScenarioDslTest extends BaseConfigurableTest {
    protected ScenarioDsl scenario;

    @BeforeEach
    void setUp() {
        var configuration = loadConfiguration();
        var app = new SystemDsl(configuration);
        scenario = new ScenarioDsl(app);
    }

    @AfterEach
    void tearDown() {
        Closer.close(scenario);
    }
}
