package com.optivem.eshop.systemtest.base.v6;

import com.optivem.common.Closer;
import com.optivem.eshop.systemtest.configuration.BaseConfigurableTest;
import com.optivem.eshop.systemtest.dsl.core.app.AppDsl;
import com.optivem.eshop.systemtest.dsl.core.scenario.erp.ErpScenarioDslImpl;
import com.optivem.eshop.systemtest.dsl.port.erp.ErpScenarioDsl;
import com.optivem.testing.extensions.ChannelExtension;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(ChannelExtension.class)
public class BaseErpScenarioDslTest extends BaseConfigurableTest {
    private AppDsl app;
    protected ErpScenarioDsl scenario;

    @BeforeEach
    void setUp() {
        var configuration = loadConfiguration();
        this.app = createAppDsl(configuration);
        scenario = new ErpScenarioDslImpl(app);
    }

    @AfterEach
    void tearDown() {
        Closer.close(app);
    }
}
